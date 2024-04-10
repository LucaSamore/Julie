package com.example.julie.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.julie.components.InterestModalBottomSheet
import com.example.julie.components.PasswordTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignUpScreen(
    modifier: Modifier,
    signUpViewModel: SignUpViewModel,
    paddingValues: PaddingValues,
    onBackToSignInScreen: () -> Unit,
    onSignedUp: () -> Unit
) {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var emailAddress by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var showDateDialog by rememberSaveable { mutableStateOf(false) }
    var showInterestSheet by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    Column(
        modifier = modifier.fillMaxSize().padding(paddingValues),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign Up Screen")

        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text(text = "First name") }
        )

        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text(text = "Last name") }
        )

        Button(onClick = { showDateDialog = true }) { Text(text = "Select Birth Date") }

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username") }
        )

        TextField(
            value = emailAddress,
            onValueChange = { emailAddress = it },
            label = { Text(text = "Email address") }
        )

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

        Button(onClick = onSignedUp) { Text(text = "Sign Up") }

        Button(onClick = onBackToSignInScreen) { Text(text = "Back to sign in") }
    }
}
