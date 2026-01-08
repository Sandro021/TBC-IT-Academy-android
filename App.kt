package com.example.homework_20

import android.app.Application
import android.app.NotificationChannel
import com.example.homework_20.presentation.common.service.NotificationChannels
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationChannels.create(this)
    }
}