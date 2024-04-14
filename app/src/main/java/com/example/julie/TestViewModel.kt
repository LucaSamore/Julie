package com.example.julie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.authentication.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TestViewModel @Inject constructor(private val signOutUseCase: SignOutUseCase) : ViewModel() {

    private val _testScreenState = MutableStateFlow<TestScreenState>(value = Lce.Loading)

    val testScreenState = _testScreenState.asStateFlow()

    fun signOut() =
        viewModelScope.launch {
            _testScreenState.update { Lce.Loading }
            signOutUseCase()
                .fold(
                    { error -> _testScreenState.update { Lce.Failure(error) } },
                    { success -> _testScreenState.update { Lce.Content(success) } }
                )
        }
}
