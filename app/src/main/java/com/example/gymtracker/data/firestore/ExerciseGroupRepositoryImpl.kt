package com.example.gymtracker.data.firestore

import android.util.Log
import com.example.gymtracker.domain.model.ExerciseGroup
import com.example.gymtracker.domain.repository.ExerciseGroupRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ExerciseGroupRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ExerciseGroupRepository {
    override suspend fun seedDefaultGroupsIfEmpty() {
        val uid = auth.currentUser?.uid ?: error("User not logged in")


        val groupsRef = firestore.collection("users").document(uid).collection("exerciseGroups")
        val existing = groupsRef.limit(1).get().await()
        if (!existing.isEmpty) {
            return
        }

        val groups = listOf(
            "stretching" to ExerciseGroupDto("Stretching", "stretching", 1, 0),
            "cardio" to ExerciseGroupDto("Cardio", "cardio", 2, 0),
            "chest" to ExerciseGroupDto("Chest", "chest", 3, 0),
            "back" to ExerciseGroupDto("Back", "back", 4, 0),
            "arms" to ExerciseGroupDto("Arms", "arms", 5, 0),
            "legs" to ExerciseGroupDto("Legs", "legs", 6, 0),
            "shoulders" to ExerciseGroupDto("Shoulders", "shoulders", 7, 0),
            "abs" to ExerciseGroupDto("ABS", "abs", 8, 0),
        )
        firestore.runBatch { batch ->
            for ((id, dto) in groups) {
                batch.set(groupsRef.document(id), dto)
            }
        }.await()

    }

    override fun observeGroups(): Flow<List<ExerciseGroup>> = callbackFlow {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            this@callbackFlow.close(IllegalStateException("User not logged in"))
            return@callbackFlow
        }
        val ref = firestore.collection("users")
            .document(uid).collection("exerciseGroups")

        var reg: ListenerRegistration? = null
        reg = ref.addSnapshotListener { snap, err ->
            val chest = snap?.documents?.firstOrNull { it.id == "chest" }?.data
            Log.d("GROUPS", "Chest doc = $chest")
            if (err != null) {
                close(err)
                return@addSnapshotListener
            }
            val list = snap?.documents.orEmpty().map { doc ->
                ExerciseGroup(
                    id = doc.id,
                    title = doc.getString("title").orEmpty(),
                    icon = doc.getString("icon").orEmpty(),
                    orderIndex = (doc.get("orderIndex") as? Number)?.toInt() ?: 0,
                    exerciseCount = (doc.get("exerciseCount") as? Number)?.toInt() ?: 0
                )
            }.sortedBy { it.orderIndex }

            trySend(list)
        }

        awaitClose { reg.remove() }
    }
}

