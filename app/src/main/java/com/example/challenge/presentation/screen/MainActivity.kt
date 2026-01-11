package com.example.challenge.presentation.screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.challenge.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        NotificationChannels.create(this)
        askNotificationPermissionIfNeeded()
    }


    private fun askNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {

                }

                shouldShowRequestPermissionRationale(
                    Manifest.permission.POST_NOTIFICATIONS
                ) -> {

                    showNotificationRationale()
                }

                else -> {
                    requestPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }
            }
        }
    }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {

            } else {

            }
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showNotificationRationale() {
        AlertDialog.Builder(this)
            .setTitle("Notifications")
            .setMessage("We use notifications to keep you updated.")
            .setPositiveButton("Allow") { _, _ ->
                requestPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
            .setNegativeButton("No thanks", null)
            .show()
    }
}