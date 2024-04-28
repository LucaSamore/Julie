package com.example.julie.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.statistics.AppDto
import com.example.domain.statistics.GetCurrentScreenTimeUseCase
import com.example.domain.statistics.GetFavouriteAppsUseCase
import com.example.domain.user.GetUserProfileGamificationDataUseCase
import com.example.julie.HomeScreenState
import com.example.julie.Lce
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeScreenContent(
    val threshold: Long = 0L,
    val points: Int = 0,
    val streakValue: Int = 0,
    val favouriteApps: List<AppDto> = emptyList()
)

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val getCurrentScreenTimeUseCase: GetCurrentScreenTimeUseCase,
    private val getUserProfileGamificationDataUseCase: GetUserProfileGamificationDataUseCase,
    private val getFavouriteAppsUseCase: GetFavouriteAppsUseCase,
) : ViewModel() {

    private val _homeScreenState = MutableStateFlow<HomeScreenState>(value = Lce.Loading)
    private val _screenTimeState = MutableStateFlow(value = 0L)

    val homeScreenState = _homeScreenState.asStateFlow()
    val screenTimeState = _screenTimeState.asStateFlow()

    fun getCurrentScreenTime() {
        _screenTimeState.update { getCurrentScreenTimeUseCase() }
    }

    fun getContent() =
        viewModelScope.launch {
            _homeScreenState.update { Lce.Loading }
            getUserProfileGamificationDataUseCase()
                .fold(
                    { error -> _homeScreenState.update { Lce.Failure(error) } },
                    { dto ->
                        getFavouriteAppsUseCase()
                            .fold(
                                { error -> _homeScreenState.update { Lce.Failure(error) } },
                                { apps ->
                                    _homeScreenState.update {
                                        Lce.Content(
                                            HomeScreenContent(
                                                threshold = dto.threshold,
                                                points = dto.points,
                                                streakValue = dto.currentStreakValue,
                                                favouriteApps = apps
                                            )
                                        )
                                    }
                                }
                            )
                    }
                )
        }
}
