package com.example.julie.verifyemail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.authentication.VerifyEmailUseCase
import com.example.julie.Lce
import com.example.julie.VerifyEmailState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(private val verifyEmailUseCase: VerifyEmailUseCase) :
    ViewModel() {

    private val _verifyEmailScreenState = MutableStateFlow<VerifyEmailState>(value = Lce.Loading)

    val verifyEmailScreenState = _verifyEmailScreenState.asStateFlow()

    fun sendVerificationEmail() =
        viewModelScope.launch {
            _verifyEmailScreenState.update { Lce.Loading }
            verifyEmailUseCase()
                .fold(
                    { errors -> _verifyEmailScreenState.update { Lce.Failure(errors) } },
                    { success -> _verifyEmailScreenState.update { Lce.Content(success) } },
                )
        }
}
