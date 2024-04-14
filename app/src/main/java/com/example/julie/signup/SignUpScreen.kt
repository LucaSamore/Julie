package com.example.julie.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
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
import com.example.data.user.BirthDateProblem
import com.example.data.user.EmailAddressProblem
import com.example.data.user.FirstNameProblem
import com.example.data.user.LastNameProblem
import com.example.data.user.PasswordProblem
import com.example.data.user.UserProblem
import com.example.data.user.UsernameProblem
import com.example.julie.Lce
import com.example.julie.components.InterestModalBottomSheet
import com.example.julie.components.PasswordTextField
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignUpScreen(
    modifier: Modifier,
    signUpViewModel: SignUpViewModel,
    paddingValues: PaddingValues,
    onBackToSignInScreen: () -> Unit,
    onSignedUp: () -> Unit
) {
    val state by signUpViewModel.signUpScreenState.collectAsState()

    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var emailAddress by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var showDateDialog by rememberSaveable { mutableStateOf(false) }
    var showInterestSheet by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    var firstNameValidationError by rememberSaveable { mutableStateOf("") }
    var firstNameValidationErrorHidden by rememberSaveable { mutableStateOf(true) }
    var lastNameValidationError by rememberSaveable { mutableStateOf("") }
    var lastNameValidationErrorHidden by rememberSaveable { mutableStateOf(true) }
    var birthdateValidationError by rememberSaveable { mutableStateOf("") }
    var birthdateValidationErrorHidden by rememberSaveable { mutableStateOf(true) }
    var usernameValidationError by rememberSaveable { mutableStateOf("") }
    var usernameValidationErrorHidden by rememberSaveable { mutableStateOf(true) }
    var emailValidationError by rememberSaveable { mutableStateOf("") }
    var emailValidationErrorHidden by rememberSaveable { mutableStateOf(true) }
    var passwordValidationError by rememberSaveable { mutableStateOf("") }
    var passwordValidationErrorHidden by rememberSaveable { mutableStateOf(true) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var errorMessageHidden by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier =
            modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign Up Screen")

        if (!firstNameValidationErrorHidden) {
            Text(text = firstNameValidationError, color = Color.Red, textAlign = TextAlign.Center)
        }

        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text(text = "First name") }
        )

        if (!lastNameValidationErrorHidden) {
            Text(text = lastNameValidationError, color = Color.Red, textAlign = TextAlign.Center)
        }

        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text(text = "Last name") }
        )

        if (!birthdateValidationErrorHidden) {
            Text(text = birthdateValidationError, color = Color.Red, textAlign = TextAlign.Center)
        }

        Button(onClick = { showDateDialog = true }) { Text(text = "Select Birth Date") }

        if (!usernameValidationErrorHidden) {
            Text(text = usernameValidationError, color = Color.Red, textAlign = TextAlign.Center)
        }

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username") }
        )

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

        if (showInterestSheet) {
            InterestModalBottomSheet(onDismiss = { showInterestSheet = false }, modifier = modifier)
        }

        if (showDateDialog) {
            DatePickerDialog(
                onDismissRequest = { showDateDialog = false },
                confirmButton = { TextButton(onClick = { showDateDialog = false }) { Text("Ok") } },
                dismissButton = {
                    TextButton(onClick = { showDateDialog = false }) { Text("Cancel") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        Button(onClick = { showInterestSheet = true }) { Text(text = "Select Interest") }

        Button(
            onClick = {
                signUpViewModel.signUp(
                    firstName,
                    lastName,
                    LocalDate.now(),
                    username,
                    emailAddress,
                    password,
                    emptyList()
                )
            }
        ) {
            Text(text = "Sign Up")
        }

        Button(onClick = onBackToSignInScreen) { Text(text = "Back to sign in") }

        if (!errorMessageHidden) {
            Text(text = errorMessage, color = Color.Red, textAlign = TextAlign.Center)
        }

        when (val currentState = state) {
            is Lce.Loading -> {
                firstNameValidationErrorHidden = true
                lastNameValidationErrorHidden = true
                birthdateValidationErrorHidden = true
                usernameValidationErrorHidden = true
                emailValidationErrorHidden = true
                passwordValidationErrorHidden = true
                errorMessageHidden = true
            }
            is Lce.Content -> {
                onSignedUp()
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
                                is FirstNameProblem -> {
                                    firstNameValidationError = problem.message
                                    firstNameValidationErrorHidden = false
                                }
                                is LastNameProblem -> {
                                    lastNameValidationError = problem.message
                                    lastNameValidationErrorHidden = false
                                }
                                is BirthDateProblem -> {
                                    birthdateValidationError = problem.message
                                    birthdateValidationErrorHidden = false
                                }
                                is UsernameProblem -> {
                                    usernameValidationError = problem.message
                                    usernameValidationErrorHidden = false
                                }
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
