package com.example.julie.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.NonEmptySet
import com.example.data.user.Interest
import com.example.domain.authentication.CreateAccountDto
import com.example.domain.authentication.SignUpUseCase
import com.example.julie.Lce
import com.example.julie.SignUpScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase) : ViewModel() {

    private val _signUpScreenState = MutableStateFlow<SignUpScreenState>(value = Lce.Loading)

    val signUpScreenState = _signUpScreenState.asStateFlow()

    fun signUp(
        firstName: String,
        lastName: String,
        birthDate: LocalDate,
        username: String,
        emailAddress: String,
        password: String,
        interest: NonEmptySet<Interest>
    ) = viewModelScope.launch {
        _signUpScreenState.update { Lce.Loading }
        signUpUseCase(
            CreateAccountDto(
                firstName,
                lastName,
                birthDate,
                username,
                emailAddress,
                password,
                interest
            )
        ).fold(
            { errors -> _signUpScreenState.update { Lce.Failure(errors) } },
            { success -> _signUpScreenState.update { Lce.Content(success) } }
        )
    }
}
