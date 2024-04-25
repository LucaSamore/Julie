package com.example.julie.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.julie.Lce

@Composable
internal fun SettingsScreen(
    modifier: Modifier,
    settingsViewModel: SettingsViewModel,
    paddingValues: PaddingValues,
    onSignOut: () -> Unit
) {

    val state by settingsViewModel.settingsScreenState.collectAsState()
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var errorMessageHidden by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = modifier.fillMaxSize().padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Test Screen")

        if (!errorMessageHidden) {
            Text(text = errorMessage, color = Color.Red, textAlign = TextAlign.Center)
        }

        Button(onClick = { settingsViewModel.signOut() }) { Text(text = "Sign Out") }

        when (val currentState = state) {
            is Lce.Loading -> {
                errorMessageHidden = true
            }
            is Lce.Content -> {
                onSignOut()
            }
            is Lce.Failure -> {
                errorMessage = currentState.error.message
                errorMessageHidden = false
            }
        }
    }
}
