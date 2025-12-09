package com.example.homework_28.presentation.extension

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toFeedDateString(): String {
    val instant = Instant.ofEpochMilli(this)
    val zoned = instant.atZone(ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("d MMMM 'at' h:mm a", Locale.getDefault())
    return zoned.format(formatter)
}