package com.example.julie

import android.app.AppOpsManager
import android.app.AppOpsManager.MODE_ALLOWED
import android.app.AppOpsManager.OPSTR_GET_USAGE_STATS
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.data.authentication.AuthenticationService
import com.example.data.di.FirebaseService
import com.example.data.statistics.NotificationListener
import com.example.julie.navigation.Destination
import com.example.julie.ui.theme.NeobrutalismTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject @FirebaseService lateinit var authenticationService: AuthenticationService

    private lateinit var startDestination: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!checkUsageStatsPermission()) {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }

        if (!isNotificationListenerServiceEnabled()) {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        startNotificationListenerService()

        setContent {
            NeobrutalismTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = NeobrutalismTheme.colors.background
                ) {
                    JulieApplication(startDestination = startDestination)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        startDestination =
            if (authenticationService.isUserLoggedIn()) {
                if (authenticationService.isEmailVerified()) {
                    Destination.Test.name
                } else {
                    Destination.VerifyEmail.name
                }
            } else {
                Destination.SignIn.name
            }
    }

    private fun checkUsageStatsPermission(): Boolean {
        val appOpsManager = getSystemService(APP_OPS_SERVICE) as AppOpsManager
        val mode =
            appOpsManager.unsafeCheckOpNoThrow(OPSTR_GET_USAGE_STATS, Process.myUid(), packageName)
        return mode == MODE_ALLOWED
    }

    private fun isNotificationListenerServiceEnabled(): Boolean {
        val packageName = packageName
        val flat = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return flat
            .split(":")
            .mapNotNull { ComponentName.unflattenFromString(it) }
            .any { it.packageName == packageName }
    }

    private fun startNotificationListenerService() {
        val intent = Intent(this, NotificationListener::class.java)
        startService(intent)
    }

}
