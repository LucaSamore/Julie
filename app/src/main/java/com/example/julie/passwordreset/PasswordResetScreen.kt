package com.example.julie.passwordreset

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.example.data.user.UserProblem
import com.example.julie.Lce
import com.example.julie.components.neubrutalism.NeubrutalPrimaryButton
import com.example.julie.components.neubrutalism.NeubrutalSecondaryButton
import com.example.julie.components.neubrutalism.NeubrutalTextField
import com.example.julie.ui.theme.NeobrutalismTheme

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
        Text(
            text = "Password Reset",
            modifier = modifier.fillMaxWidth(),
            style = NeobrutalismTheme.typography.mainTitle,
            textAlign = TextAlign.Center
        )

        NeubrutalTextField(
            modifier = modifier,
            value = emailAddress,
            placeholder = "test@gmail.com",
            label = "Email Address",
            errorMessage = emailValidationError,
            errorMessageHidden = emailValidationErrorHidden
        ) {
            emailAddress = it
        }

        if (!resetPasswordEmailHiddem) {
            Text(text = resetPasswordEmail, textAlign = TextAlign.Center)
        }

        if (!errorMessageHidden) {
            Text(text = errorMessage, color = Color.Red, textAlign = TextAlign.Center)
        }

        NeubrutalPrimaryButton(
            modifier = modifier,
            text = "SEND PASSWORD RESET EMAIL",
            height = 64.dp
        ) {
            passwordResetViewModel.sendResetPasswordEmail(emailAddress)
        }

        NeubrutalSecondaryButton(modifier = modifier, text = "Back") { onBackToSignInScreen() }
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
