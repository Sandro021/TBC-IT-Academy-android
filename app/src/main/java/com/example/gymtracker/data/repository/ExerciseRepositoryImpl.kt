package com.example.gymtracker.data.repository

import com.example.gymtracker.domain.model.Exercise
import com.example.gymtracker.domain.repository.ExerciseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ExerciseRepository {
    override suspend fun createExercise(
        name: String,
        groupId: String,
        groupTitle: String
    ) {
        val uid = auth.currentUser?.uid ?: error("User not logged in")
        val userDoc = firestore.collection("users").document(uid)
        val exerciseRef = userDoc.collection("exercises")
        val groupDoc = userDoc.collection("exerciseGroups").document(groupId)

        firestore.runTransaction { tx ->
            val newExerciseDoc = exerciseRef.document()
            tx.set(
                newExerciseDoc, mapOf(
                    "name" to name,
                    "groupId" to groupId,
                    "groupTitle" to groupTitle,
                    "createdAt" to FieldValue.serverTimestamp()
                )
            )
            tx.set(
                groupDoc,
                mapOf("exerciseCount" to FieldValue.increment(1)),
                com.google.firebase.firestore.SetOptions.merge()
            )
        }.await()
    }

    override fun observeExerciseByGroup(groupId: String) = callbackFlow {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            close(IllegalStateException("User not logged in"))
            return@callbackFlow
        }

        val ref = firestore.collection("users").document(uid)
            .collection("exercises")
            .whereEqualTo("groupId", groupId)

        val reg = ref.addSnapshotListener { snap, err ->
            if (err != null) {
                close(err)
                return@addSnapshotListener
            }

            val list = snap?.documents.orEmpty().map { doc ->
                Exercise(
                    id = doc.id,
                    name = doc.getString("name").orEmpty(),
                    groupId = doc.getString("groupId").orEmpty()
                )
            }.sortedBy { it.name }

            trySend(list)
        }

        awaitClose { reg.remove() }
    }

    override suspend fun deleteExercise(exerciseId: String) {
        val uid = auth.currentUser?.uid ?: error("User not logged in")
        val userDoc = firestore.collection("users").document(uid)
        val exDoc = userDoc.collection("exercises").document(exerciseId)

        firestore.runTransaction { tx ->
            val snap = tx.get(exDoc)
            if (!snap.exists()) return@runTransaction

            val groupId = snap.getString("groupId").orEmpty()
            tx.delete(exDoc)

            if (groupId.isNotBlank()) {
                val groupDoc = userDoc.collection("exerciseGroups").document(groupId)
                tx.set(
                    groupDoc,
                    mapOf("exerciseCount" to FieldValue.increment(-1)),
                    com.google.firebase.firestore.SetOptions.merge()
                )
            }
        }.await()
    }
}