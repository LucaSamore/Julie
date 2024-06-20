package com.example.julie.verifyemail

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
import com.example.data.authentication.AuthenticationProblem
import com.example.julie.Lce
import com.example.julie.components.neubrutalism.NeubrutalPrimaryButton
import com.example.julie.components.neubrutalism.NeubrutalSecondaryButton
import com.example.julie.ui.theme.NeobrutalismTheme

@Composable
internal fun VerifyEmailScreen(
    modifier: Modifier,
    verifyEmailViewModel: VerifyEmailViewModel,
    paddingValues: PaddingValues,
    onGoToSignInScreen: () -> Unit
) {
    val state by verifyEmailViewModel.verifyEmailScreenState.collectAsState()

    var errorMessage by rememberSaveable { mutableStateOf("") }
    var errorMessageHidden by rememberSaveable { mutableStateOf(true) }

    var verificationEmailSentMessage by rememberSaveable { mutableStateOf("") }
    var verificationEmailSentMessageHidden by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = modifier.fillMaxSize().padding(paddingValues),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Email Verification",
            modifier = modifier.fillMaxWidth(.9f),
            style = NeobrutalismTheme.typography.mainTitle,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Your email address is not verified",
            modifier = modifier.fillMaxWidth(.9f),
            style = NeobrutalismTheme.typography.screenTitle,
            textAlign = TextAlign.Center
        )

        if (!errorMessageHidden) {
            Text(text = errorMessage, color = Color.Red, textAlign = TextAlign.Center)
        }

        NeubrutalPrimaryButton(
            modifier = modifier,
            text = "SEND VERIFICATION EMAIL",
            width = .8f,
            height = 64.dp
        ) {
            verifyEmailViewModel.sendVerificationEmail()
        }

        NeubrutalSecondaryButton(modifier = modifier, text = "Back to login") {
            onGoToSignInScreen()
        }
    }

    when (val currentState = state) {
        is Lce.Loading -> {
            errorMessageHidden = true
            verificationEmailSentMessageHidden = true
        }
        is Lce.Content -> {
            verificationEmailSentMessage = "Check your email for verification"
            verificationEmailSentMessageHidden = false
        }
        is Lce.Failure -> {
            when (currentState.error) {
                is AuthenticationProblem -> {
                    errorMessage = currentState.error.message
                    errorMessageHidden = false
                }
                else -> Unit
            }
        }
    }
}
