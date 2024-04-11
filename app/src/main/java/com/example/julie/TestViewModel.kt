package com.example.julie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.authentication.AuthenticationService
import com.example.data.authentication.UserSignedOut
import com.example.data.di.FirebaseService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TestViewModel
@Inject
constructor(@FirebaseService private val authenticationService: AuthenticationService) :
    ViewModel() {

    private val _testScreenState = MutableStateFlow<TestScreenState>(value = Lce.Loading)
    val testScreenState = _testScreenState.asStateFlow()

    fun signOut() =
        viewModelScope.launch {
            _testScreenState.update { Lce.Loading }
            authenticationService
                .signOut()
                .fold(
                    { error -> _testScreenState.update { Lce.Failure(error) } },
                    { _ -> _testScreenState.update { Lce.Content(UserSignedOut) } }
                )
        }
}
