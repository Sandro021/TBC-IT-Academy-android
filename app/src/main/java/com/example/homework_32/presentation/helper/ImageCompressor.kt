package com.example.homework_32.presentation.helper


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object ImageCompressor {

    fun compress(
        context: Context,
        uri: Uri,
        quality: Int = 80
    ): Uri {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        val file = File.createTempFile(
            "compressed_",
            ".jpg",
            context.cacheDir
        )

        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

        outputStream.flush()
        outputStream.close()

        return Uri.fromFile(file)
    }
}
