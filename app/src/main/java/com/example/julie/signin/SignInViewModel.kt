package com.example.julie.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.authentication.AuthenticationService
import com.example.data.authentication.Credentials
import com.example.data.di.FirebaseService
import com.example.data.user.EmailAddress
import com.example.data.user.Password
import com.example.julie.Lce
import com.example.julie.SignInScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SignInViewModel
@Inject
constructor(@FirebaseService private val authenticationService: AuthenticationService) :
    ViewModel() {
    private val _signInScreenState = MutableStateFlow<SignInScreenState>(value = Lce.Loading)

    val signInScreenState = _signInScreenState.asStateFlow()

    fun signIn(emailAddress: String, password: String) =
        viewModelScope.launch {
            _signInScreenState.update { Lce.Loading }
            val credentials = Credentials.SignInDto(EmailAddress(emailAddress), Password(password))
            authenticationService
                .signInWithEmailAndPassword(credentials)
                .fold(
                    { error -> _signInScreenState.update { Lce.Failure(error) } },
                    { success -> _signInScreenState.update { Lce.Content(success) } }
                )
        }
}
