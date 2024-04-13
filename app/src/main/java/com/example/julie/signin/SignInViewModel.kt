package com.example.julie.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.authentication.SignInUseCase
import com.example.julie.Lce
import com.example.julie.SignInScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SignInViewModel @Inject constructor(private val signInUseCase: SignInUseCase) : ViewModel() {

    private val _signInScreenState = MutableStateFlow<SignInScreenState>(value = Lce.Loading)

    val signInScreenState = _signInScreenState.asStateFlow()

    fun signIn(emailAddress: String, password: String) =
        viewModelScope.launch {
            _signInScreenState.update { Lce.Loading }
            signInUseCase(emailAddress, password)
                .fold(
                    { errors -> _signInScreenState.update { Lce.Failure(errors) } },
                    { success -> _signInScreenState.update { Lce.Content(success) } }
                )
        }
}
