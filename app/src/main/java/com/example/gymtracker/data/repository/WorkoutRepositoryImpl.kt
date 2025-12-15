package com.example.gymtracker.data.repository

import com.example.gymtracker.domain.model.WorkoutItem
import com.example.gymtracker.domain.model.WorkoutSet
import com.example.gymtracker.domain.repository.WorkoutRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : WorkoutRepository {
    override fun observeWorkout(dateKey: String): Flow<List<WorkoutItem>> = callbackFlow {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            this@callbackFlow.close(IllegalStateException("User not logged in"))
            return@callbackFlow
        }
        val itemsRef = firestore.collection("users")
            .document(uid)
            .collection("workouts")
            .document(dateKey)
            .collection("items")

        val reg = itemsRef.addSnapshotListener { snap, err ->
            if (err != null) {
                close(err); return@addSnapshotListener
            }

            val items = snap?.documents.orEmpty().map { doc ->
                val setsRaw = doc.get("sets") as? List<Map<String, Any>> ?: emptyList()
                val sets = setsRaw.map { s ->
                    WorkoutSet(
                        number = (s["number"] as? Number)?.toInt() ?: 1,
                        weight = (s["weight"] as? Number)?.toDouble() ?: 0.0,
                        reps = (s["reps"] as? Number)?.toInt() ?: 0
                    )
                }.sortedBy { it.number }

                WorkoutItem(
                    exerciseId = doc.getString("exerciseId") ?: doc.id,
                    name = doc.getString("name").orEmpty(),
                    sets = sets
                )
            }.sortedBy { it.name }

            trySend(items)
        }

        awaitClose { reg.remove() }

    }

    override suspend fun saveWorkout(
        dateKey: String,
        items: List<WorkoutItem>
    ) {
        val uid = auth.currentUser?.uid ?: error("User not logged in")

        val workoutDoc = firestore.collection("users")
            .document(uid)
            .collection("workouts")
            .document(dateKey)

        val itemsRef = workoutDoc.collection("items")


        workoutDoc.set(
            mapOf(
                "dateKey" to dateKey,
                "updatedAt" to FieldValue.serverTimestamp()
            )
        ).await()


        firestore.runBatch { batch ->
            for (item in items) {
                val doc = itemsRef.document(item.exerciseId)
                val sets = item.sets.map { s ->
                    mapOf("number" to s.number, "weight" to s.weight, "reps" to s.reps)
                }
                batch.set(
                    doc, mapOf(
                        "exerciseId" to item.exerciseId,
                        "name" to item.name,
                        "sets" to sets,
                        "updatedAt" to FieldValue.serverTimestamp()
                    )
                )
            }
        }.await()
    }

    override suspend fun deleteWorkoutItem(dateKey: String, exerciseId: String) {
        val uid = auth.currentUser?.uid ?: error("User not logged in")

        val itemDoc = firestore.collection("users")
            .document(uid)
            .collection("workouts")
            .document(dateKey)
            .collection("items")
            .document(exerciseId)

        itemDoc.delete().await()

    }
}
