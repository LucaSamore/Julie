package com.example.julie.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.user.BirthDateProblem
import com.example.data.user.EmailAddressProblem
import com.example.data.user.FirstNameProblem
import com.example.data.user.LastNameProblem
import com.example.data.user.PasswordProblem
import com.example.data.user.UserProblem
import com.example.data.user.UsernameProblem
import com.example.julie.Lce
import com.example.julie.R
import com.example.julie.bounceClick
import com.example.julie.components.InterestModalBottomSheet
import com.example.julie.components.neubrutalism.NeubrutalErrorMessage
import com.example.julie.components.neubrutalism.NeubrutalLabel
import com.example.julie.components.neubrutalism.NeubrutalPasswordTextField
import com.example.julie.components.neubrutalism.NeubrutalPrimaryButton
import com.example.julie.components.neubrutalism.NeubrutalSecondaryButton
import com.example.julie.components.neubrutalism.NeubrutalTextField
import com.example.julie.ui.theme.NeobrutalismTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

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
        if (!errorMessageHidden) {
            NeubrutalErrorMessage(modifier = modifier, message = errorMessage)
        }

        NeubrutalTextField(
            modifier = modifier,
            value = firstName,
            placeholder = "Your first name",
            label = "First Name",
            errorMessage = firstNameValidationError,
            errorMessageHidden = firstNameValidationErrorHidden
        ) {
            firstName = it
        }

        NeubrutalTextField(
            modifier = modifier,
            value = lastName,
            placeholder = "Your last name",
            label = "Last Name",
            errorMessage = lastNameValidationError,
            errorMessageHidden = lastNameValidationErrorHidden
        ) {
            lastName = it
        }

        Column(
            modifier = modifier.padding(vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            NeubrutalLabel(modifier = modifier, text = "Birth Date")

            if (!birthdateValidationErrorHidden) {
                NeubrutalErrorMessage(modifier = modifier, message = birthdateValidationError)
            }

            NeubrutalSecondaryButton(modifier = modifier, text = "Select Date", width = 0.8f) {
                showDateDialog = true
            }
        }

        NeubrutalTextField(
            modifier = modifier,
            value = username,
            placeholder = "Your username",
            label = "Username",
            errorMessage = usernameValidationError,
            errorMessageHidden = usernameValidationErrorHidden
        ) {
            username = it
        }

        NeubrutalTextField(
            modifier = modifier,
            value = emailAddress,
            placeholder = "Your email address",
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

        Column(
            modifier = modifier.padding(vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            NeubrutalLabel(modifier = modifier, text = "Interest")

            NeubrutalSecondaryButton(modifier = modifier, text = "Select Interest", width = 0.8f) {
                showInterestSheet = true
            }
        }

        Column(
            modifier = modifier.padding(vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier.fillMaxWidth(.8f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = modifier,
                    text = "Have an account?",
                    style =
                        TextStyle(
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.inconsolata_variable)),
                            color = NeobrutalismTheme.colors.text,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                        ),
                )

                Spacer(modifier = modifier.padding(horizontal = 8.dp))

                Text(
                    modifier =
                        modifier.bounceClick().clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onBackToSignInScreen()
                        },
                    text = "Sign In",
                    style =
                        TextStyle(
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
                            color = NeobrutalismTheme.colors.text,
                            fontSize = 18.sp,
                        ),
                )
            }

            Spacer(modifier = modifier.padding(vertical = 16.dp))

            NeubrutalPrimaryButton(modifier = modifier, text = "REGISTER") {
                signUpViewModel.signUp(
                    firstName,
                    lastName,
                    if (datePickerState.selectedDateMillis == null) LocalDate.now()
                    else
                        Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate(),
                    username,
                    emailAddress,
                    password,
                    emptyList()
                )
            }
        }
    }

    if (showInterestSheet) {
        InterestModalBottomSheet(onDismiss = { showInterestSheet = false }, modifier = modifier)
    }

    if (showDateDialog) {
        DatePickerDialog(
            onDismissRequest = { showDateDialog = false },
            confirmButton = { TextButton(onClick = { showDateDialog = false }) { Text("Ok") } },
            dismissButton = { TextButton(onClick = { showDateDialog = false }) { Text("Cancel") } }
        ) {
            DatePicker(state = datePickerState)
        }
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
                    else -> {
                        errorMessage = problem.message
                        errorMessageHidden = false
                    }
                }
            }
        }
    }
}
