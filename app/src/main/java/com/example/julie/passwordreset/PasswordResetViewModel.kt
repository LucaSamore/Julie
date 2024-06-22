package com.example.julie.passwordreset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.authentication.PasswordResetUseCase
import com.example.julie.Lce
import com.example.julie.PasswordResetState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class PasswordResetViewModel
@Inject
constructor(private val passwordResetUseCase: PasswordResetUseCase) : ViewModel() {

    private val _passwordResetScreenState =
        MutableStateFlow<PasswordResetState>(value = Lce.Loading)

    val passwordResetScreenState = _passwordResetScreenState.asStateFlow()

    fun sendResetPasswordEmail(emailAddress: String) =
        viewModelScope.launch {
            _passwordResetScreenState.update { Lce.Loading }
            passwordResetUseCase(emailAddress)
                .fold(
                    { error -> _passwordResetScreenState.update { Lce.Failure(error) } },
                    { success -> _passwordResetScreenState.update { Lce.Content(success) } }
                )
        }
}
