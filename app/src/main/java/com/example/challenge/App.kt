package com.example.challenge

import android.app.Application
import com.example.challenge.presentation.service.NotificationChannels
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationChannels.create(this)
    }
}