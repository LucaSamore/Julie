package com.example.julie.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.authentication.SignOutUseCase
import com.example.julie.Lce
import com.example.julie.SettingsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class SettingsViewModel @Inject constructor(private val signOutUseCase: SignOutUseCase) :
    ViewModel() {
    private val _settingsScreenState = MutableStateFlow<SettingsScreenState>(value = Lce.Loading)

    val settingsScreenState = _settingsScreenState.asStateFlow()

    fun signOut() =
        viewModelScope.launch {
            _settingsScreenState.update { Lce.Loading }
            signOutUseCase()
                .fold(
                    { error -> _settingsScreenState.update { Lce.Failure(error) } },
                    { success -> _settingsScreenState.update { Lce.Content(success) } }
                )
        }
}
