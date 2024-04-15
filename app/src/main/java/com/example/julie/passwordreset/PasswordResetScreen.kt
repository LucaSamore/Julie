package com.example.julie.passwordreset

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.data.user.UserProblem
import com.example.julie.Lce

@Composable
internal fun PasswordResetScreen(
    modifier: Modifier,
    passwordResetViewModel: PasswordResetViewModel,
    paddingValues: PaddingValues,
    onBackToSignInScreen: () -> Unit
) {
    val state by passwordResetViewModel.passwordResetScreenState.collectAsState()

    var emailAddress by rememberSaveable { mutableStateOf("") }

    var resetPasswordEmail by rememberSaveable { mutableStateOf("") }
    var resetPasswordEmailHiddem by rememberSaveable { mutableStateOf(true) }
    var emailValidationError by rememberSaveable { mutableStateOf("") }
    var emailValidationErrorHidden by rememberSaveable { mutableStateOf(true) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var errorMessageHidden by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = modifier.fillMaxSize().padding(paddingValues),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Password Reset Screen")

        if (!emailValidationErrorHidden) {
            Text(text = emailValidationError, color = Color.Red, textAlign = TextAlign.Center)
        }

        TextField(
            value = emailAddress,
            onValueChange = { emailAddress = it },
            label = { Text(text = "Email address") }
        )

        if (!resetPasswordEmailHiddem) {
            Text(text = resetPasswordEmail, textAlign = TextAlign.Center)
        }

        if (!errorMessageHidden) {
            Text(text = errorMessage, color = Color.Red, textAlign = TextAlign.Center)
        }

        Button(onClick = { passwordResetViewModel.sendResetPasswordEmail(emailAddress) }) {
            Text(text = "Send Password Reset Email")
        }

        Button(onClick = onBackToSignInScreen) { Text(text = "Back to sign in") }
    }

    when (val currentState = state) {
        is Lce.Loading -> {
            resetPasswordEmailHiddem = true
            errorMessageHidden = true
            emailValidationErrorHidden = true
        }
        is Lce.Content -> {
            resetPasswordEmail = "Sent an email for password resetting"
            resetPasswordEmailHiddem = false
        }
        is Lce.Failure -> {
            when (currentState.error) {
                is UserProblem -> {
                    emailValidationError = currentState.error.message
                    emailValidationErrorHidden = false
                }
                else -> {
                    errorMessage = currentState.error.message
                    errorMessageHidden = false
                }
            }
        }
    }
}
