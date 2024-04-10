package com.example.julie.signup

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.julie.R

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
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
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

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username") }
        )

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

        TextField(
            value = emailAddress,
            onValueChange = { emailAddress = it },
            label = { Text(text = "Email address") }
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation =
                if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (!passwordHidden) R.drawable.baseline_visibility_24
                        else R.drawable.baseline_visibility_off_24
                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(
                        imageVector = ImageVector.vectorResource(visibilityIcon),
                        contentDescription = description
                    )
                }
            }
        )

        if (showInterestSheet) {
            InterestModalBottomSheet(onDismiss = { showInterestSheet = false }, modifier = modifier)
        }

        Button(onClick = { showDateDialog = true }) { Text(text = "Select Birth Date") }

        Button(onClick = { showInterestSheet = true }) { Text(text = "Select Interest") }

        Button(onClick = onSignedUp) { Text(text = "Sign Up") }

        Button(onClick = onBackToSignInScreen) { Text(text = "Back to sign in") }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun InterestModalBottomSheet(onDismiss: () -> Unit, modifier: Modifier) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        val category =
            listOf(
                Pair(
                    "Category 1",
                    listOf("Interest1", "Interest2", "Interest3", "Interest4", "Interest5")
                ),
                Pair(
                    "Category 2",
                    listOf("Interest1", "Interest2", "Interest3", "Interest4", "Interest5")
                ),
                Pair(
                    "Category 3",
                    listOf("Interest1", "Interest2", "Interest3", "Interest4", "Interest5")
                ),
                Pair(
                    "Category 4",
                    listOf("Interest1", "Interest2", "Interest3", "Interest4", "Interest5")
                ),
                Pair(
                    "Category 5",
                    listOf("Interest1", "Interest2", "Interest3", "Interest4", "Interest5")
                ),
            )

        LazyColumn {
            category.forEach {
                stickyHeader {
                    Row(
                        modifier = modifier.fillMaxWidth().padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = it.first)
                    }
                }

                items(it.second) { interest ->
                    Row(
                        modifier = modifier.fillMaxWidth().padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = interest)
                    }
                }
            }
        }
    }
}
