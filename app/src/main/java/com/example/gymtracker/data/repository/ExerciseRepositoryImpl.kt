package com.example.gymtracker.data.repository

import com.example.gymtracker.domain.repository.ExerciseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
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
}