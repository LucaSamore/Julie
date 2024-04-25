package com.example.julie.home

import androidx.lifecycle.ViewModel
import com.example.domain.statistics.GetCurrentScreenTimeUseCase
import com.example.julie.HomeScreenState
import com.example.julie.Lce
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeScreenContent(
    val currentScreenTime: Long = 0L,
    val threshold: Long = 0L,
    val points: Int = 0,
    val streakValue: Int = 0,
    val favouriteApps: List<String> = emptyList()
)

@HiltViewModel
class HomeViewModel
@Inject
constructor(private val getCurrentScreenTimeUseCase: GetCurrentScreenTimeUseCase) : ViewModel() {

    private val _homeScreenState = MutableStateFlow<HomeScreenState>(value = Lce.Loading)

    val homeScreenState = _homeScreenState.asStateFlow()

    fun getCurrentScreenTime() {
        _homeScreenState.update { Lce.Loading }
        _homeScreenState.update {
            Lce.Content(
                HomeScreenContent(
                    currentScreenTime = getCurrentScreenTimeUseCase(),
                )
            )
        }
    }
}
