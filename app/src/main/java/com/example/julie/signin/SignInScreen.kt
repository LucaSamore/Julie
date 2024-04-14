package com.example.julie.signin

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
import com.example.data.authentication.AuthenticationProblem
import com.example.data.user.EmailAddressProblem
import com.example.data.user.PasswordProblem
import com.example.data.user.UserProblem
import com.example.julie.Lce
import com.example.julie.components.PasswordTextField

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
        Text(text = "Sign In Screen")

        if (!emailValidationErrorHidden) {
            Text(text = emailValidationError, color = Color.Red, textAlign = TextAlign.Center)
        }

        TextField(
            value = emailAddress,
            onValueChange = { emailAddress = it },
            label = { Text(text = "Email address") }
        )

        if (!passwordValidationErrorHidden) {
            Text(text = passwordValidationError, color = Color.Red, textAlign = TextAlign.Center)
        }

        PasswordTextField(modifier = modifier, password = password) { password = it }

        Button(onClick = { signInViewModel.signIn(emailAddress, password) }) {
            Text(text = "Sign In")
        }

        Button(onClick = onGoToSignUpScreen) { Text(text = "Goto Sign Up") }

        Button(onClick = onGoToPasswordResetScreen) { Text(text = "Forgot password?") }

        if (!errorMessageHidden) {
            Text(text = errorMessage, color = Color.Red, textAlign = TextAlign.Center)
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
                        is AuthenticationProblem -> {
                            errorMessage = problem.message
                            errorMessageHidden = false
                        }
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
                        else -> Unit
                    }
                }
            }
        }
    }
}
