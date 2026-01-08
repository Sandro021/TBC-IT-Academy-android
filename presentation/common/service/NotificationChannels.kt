package com.example.homework_20.presentation.common.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationChannels {
    const val PROFILE_CHANNEL_ID = "profile_channel"
    const val MAIN_CHANNEL_ID = "main_channel"

    fun create(context : Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val profileChannel = NotificationChannel(
                PROFILE_CHANNEL_ID,
                "Profile Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications related to user profile"
            }

            val mainChannel = NotificationChannel(
                MAIN_CHANNEL_ID,
                "Main Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Main screen notifications"
            }

            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(profileChannel)
            manager.createNotificationChannel(mainChannel)
        }
    }

}