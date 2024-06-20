package com.example.julie.passwordreset

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.example.julie.components.neubrutalism.NeubrutalErrorMessage
import com.example.julie.components.neubrutalism.NeubrutalLabel
import com.example.julie.components.neubrutalism.NeubrutalPrimaryButton
import com.example.julie.components.neubrutalism.NeubrutalSecondaryButton
import com.example.julie.components.neubrutalism.NeubrutalTextField
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation

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
            modifier = modifier.fillMaxWidth(.9f),
            style = NeobrutalismTheme.typography.mainTitle,
            textAlign = TextAlign.Center
        )

        Box(modifier = modifier.fillMaxWidth(.9f).fillMaxHeight(.5f).neubrutalismElevation()) {
            Column(
                modifier =
                    modifier.fillMaxSize().background(NeobrutalismTheme.colors.contentPrimary),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NeubrutalLabel(modifier = modifier.fillMaxWidth(.8f), text = "Email Address")

                if (!emailValidationErrorHidden) {
                    NeubrutalErrorMessage(modifier = modifier, message = emailValidationError)
                }

                NeubrutalTextField(
                    modifier = modifier,
                    value = emailAddress,
                    placeholder = "test@gmail.com"
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
                    width = .8f,
                    height = 64.dp
                ) {
                    passwordResetViewModel.sendResetPasswordEmail(emailAddress)
                }
            }
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
