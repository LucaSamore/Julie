package com.example.julie.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.user.EmailAddressProblem
import com.example.data.user.PasswordProblem
import com.example.data.user.UserProblem
import com.example.julie.Lce
import com.example.julie.R
import com.example.julie.components.NeubrutalErrorMessage
import com.example.julie.components.NeubrutalLabel
import com.example.julie.components.NeubrutalPasswordTextField
import com.example.julie.components.NeubrutalTextField
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation

@Composable
internal fun SignInScreen(
    modifier: Modifier,
    signInViewModel: SignInViewModel,
    paddingValues: PaddingValues,
    onGoToSignUpScreen: () -> Unit,
    onGoToPasswordResetScreen: () -> Unit,
    onSignedIn: () -> Unit,
) {
    val state by signInViewModel.signInScreenState.collectAsState()

    var emailAddress by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var emailValidationError by rememberSaveable { mutableStateOf("") }
    var emailValidationErrorHidden by rememberSaveable { mutableStateOf(true) }
    var passwordValidationError by rememberSaveable { mutableStateOf("") }
    var passwordValidationErrorHidden by rememberSaveable { mutableStateOf(true) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var errorMessageHidden by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = modifier.fillMaxSize().padding(paddingValues),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Demo App",
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
                if (!errorMessageHidden) {
                    NeubrutalErrorMessage(message = errorMessage)
                }

                NeubrutalLabel(modifier = modifier.fillMaxWidth(.8f), text = "Email Address")

                if (!emailValidationErrorHidden) {
                    NeubrutalErrorMessage(message = emailValidationError)
                }

                NeubrutalTextField(
                    modifier = modifier,
                    value = emailAddress,
                    placeholder = "test@gmail.com"
                ) {
                    emailAddress = it
                }

                NeubrutalLabel(modifier = modifier.fillMaxWidth(.8f), text = "Password")

                if (!passwordValidationErrorHidden) {
                    NeubrutalErrorMessage(message = passwordValidationError)
                }

                NeubrutalPasswordTextField(modifier = modifier, password = password) {
                    password = it
                }
            }
        }

        Text(
            modifier = modifier.fillMaxWidth(),
            text = "New User?",
            style =
                TextStyle(
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.inconsolata_variable)),
                    color = NeobrutalismTheme.colors.text,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
        )

        Button(
            modifier = modifier.fillMaxWidth(.5f).height(48.dp).neubrutalismElevation(),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = NeobrutalismTheme.colors.buttonSecondary,
                    contentColor = NeobrutalismTheme.colors.text,
                    disabledContainerColor = NeobrutalismTheme.colors.buttonSecondary,
                    disabledContentColor = NeobrutalismTheme.colors.buttonSecondary,
                ),
            shape = NeobrutalismTheme.shapes.surface,
            onClick = { onGoToSignUpScreen() }
        ) {
            Text(
                text = "Register Now",
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = NeobrutalismTheme.typography.buttons
            )
        }

        Text(
            modifier = modifier.fillMaxWidth().clickable { onGoToPasswordResetScreen() },
            text = "Forgot password?",
            style =
                TextStyle(
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.inconsolata_variable)),
                    color = NeobrutalismTheme.colors.text,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                ),
        )

        Button(
            modifier = modifier.fillMaxWidth(.9f).height(48.dp).neubrutalismElevation(),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = NeobrutalismTheme.colors.buttonPrimary,
                    contentColor = NeobrutalismTheme.colors.text,
                    disabledContainerColor = NeobrutalismTheme.colors.buttonPrimary,
                    disabledContentColor = NeobrutalismTheme.colors.buttonPrimary,
                ),
            shape = NeobrutalismTheme.shapes.surface,
            onClick = { signInViewModel.signIn(emailAddress, password) }
        ) {
            Text(
                text = "LOGIN",
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = NeobrutalismTheme.typography.buttons
            )
        }
    }

    when (val currentState = state) {
        is Lce.Loading -> {
            errorMessageHidden = true
            emailValidationErrorHidden = true
            passwordValidationErrorHidden = true
        }
        is Lce.Content -> {
            onSignedIn()
        }
        is Lce.Failure -> {
            currentState.error.forEach { problem ->
                when (problem) {
                    is UserProblem -> {
                        when (problem) {
                            is EmailAddressProblem -> {
                                emailValidationError = problem.message
                                emailValidationErrorHidden = false
                            }
                            is PasswordProblem -> {
                                passwordValidationError = problem.message
                                passwordValidationErrorHidden = false
                            }
                            else -> Unit
                        }
                    }
                    else -> {
                        errorMessage = problem.message
                        errorMessageHidden = false
                    }
                }
            }
        }
    }
}
