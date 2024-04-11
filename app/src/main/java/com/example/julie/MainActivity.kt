package com.example.julie

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.data.authentication.AuthenticationService
import com.example.data.di.FirebaseService
import com.example.julie.navigation.Destination
import com.example.julie.ui.theme.JulieTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject @FirebaseService lateinit var authenticationService: AuthenticationService

    private lateinit var startDestination: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JulieTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JulieApplication(startDestination = startDestination)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        authenticationService
            .isUserLoggedIn()
            .fold(
                {
                    Log.e(TAG, it.message)
                    startDestination = Destination.SignIn.name
                },
                { if (it) startDestination = Destination.Test.name else Destination.SignIn.name }
            )
    }
}

const val TAG = "start"
