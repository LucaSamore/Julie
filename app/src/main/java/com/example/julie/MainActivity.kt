package com.example.julie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.data.authentication.AuthenticationService
import com.example.data.di.FirebaseService
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
}
