package com.example.homework_32.data

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class FirebaseStorageDataSource @Inject constructor(
    private val storage: FirebaseStorage,
) {
    suspend fun uploadImage(uri: Uri): String {
        val ref = storage.reference
            .child("images/${UUID.randomUUID()}.jpg")

        ref.putFile(uri).await()
        return ref.downloadUrl.await().toString()
    }
}