package com.example.julie.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.authentication.AuthenticationError
import com.example.data.authentication.AuthenticationProblem
import com.example.data.authentication.EmailNotVerified
import com.example.data.user.EmailAddressProblem
import com.example.data.user.PasswordProblem
import com.example.data.user.UserProblem
import com.example.julie.Lce
import com.example.julie.R
import com.example.julie.bounceClick
import com.example.julie.components.neubrutalism.NeubrutalErrorMessage
import com.example.julie.components.neubrutalism.NeubrutalPasswordTextField
import com.example.julie.components.neubrutalism.NeubrutalPrimaryButton
import com.example.julie.components.neubrutalism.NeubrutalTextField
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation

@Composable
internal fun SignInScreen(
    modifier: Modifier,
    signInViewModel: SignInViewModel,
    paddingValues: PaddingValues,
    onEmailNotVerified: () -> Unit,
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

        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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

            NeubrutalPasswordTextField(
                modifier = modifier,
                password = password,
                errorMessage = passwordValidationError,
                errorMessageHidden = passwordValidationErrorHidden
            ) {
                password = it
            }

            if (!errorMessageHidden) {
                NeubrutalErrorMessage(modifier = modifier, message = errorMessage)
            }
        }

        Column(
            modifier = modifier.fillMaxWidth(.8f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = "New User?",
                style =
                    TextStyle(
                        textAlign = TextAlign.Start,
                        fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
                        color = NeobrutalismTheme.colors.text,
                        fontSize = 32.sp,
                    ),
            )

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Arrow",
                    modifier = modifier.fillMaxWidth(.45f)
                )

                Button(
                    modifier =
                        modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .graphicsLayer(rotationZ = 20f)
                            .bounceClick()
                            .neubrutalismElevation(),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = NeobrutalismTheme.colors.buttonSecondary,
                            contentColor = NeobrutalismTheme.colors.text,
                            disabledContainerColor = NeobrutalismTheme.colors.buttonSecondary,
                            disabledContentColor = NeobrutalismTheme.colors.buttonSecondary,
                        ),
                    shape = NeobrutalismTheme.shapes.surface,
                    onClick = onGoToSignUpScreen
                ) {
                    Text(
                        text = "Register",
                        modifier = modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = NeobrutalismTheme.typography.buttons
                    )
                }
            }
        }

        Text(
            modifier =
                modifier.fillMaxWidth(.5f).bounceClick().clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onGoToPasswordResetScreen()
                },
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

        NeubrutalPrimaryButton(modifier = modifier, text = "LOGIN") {
            signInViewModel.signIn(emailAddress, password)
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
                    is AuthenticationProblem -> {
                        when (problem) {
                            is AuthenticationError -> {
                                errorMessage = problem.message
                                errorMessageHidden = false
                            }
                            is EmailNotVerified -> {
                                onEmailNotVerified()
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
