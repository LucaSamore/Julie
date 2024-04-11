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
import com.example.julie.Lce
import com.example.julie.components.PasswordTextField

@Composable
internal fun SignInScreen(
    modifier: Modifier,
    signInViewModel: SignInViewModel,
    paddingValues: PaddingValues,
    onGoToSignUpScreen: () -> Unit,
    onSignedIn: () -> Unit
) {
    val state by signInViewModel.signInScreenState.collectAsState()

    var emailAddress by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize().padding(paddingValues),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign In Screen")

        TextField(
            value = emailAddress,
            onValueChange = { emailAddress = it },
            label = { Text(text = "Email address") }
        )

        PasswordTextField(modifier = modifier, password = password) { password = it }

        Button(onClick = onSignedIn) { Text(text = "Sign In") }

        Button(onClick = onGoToSignUpScreen) { Text(text = "Go to sign up") }

        when (state) {
            is Lce.Loading -> {
                TODO()
            }
            is Lce.Content -> {
                TODO()
            }
            is Lce.Failure -> {
                TODO()
            }
        }
    }
}
