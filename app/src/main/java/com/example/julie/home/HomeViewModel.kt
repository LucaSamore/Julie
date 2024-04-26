package com.example.julie.home

import androidx.lifecycle.ViewModel
import com.example.data.di.IoDispatcher
import com.example.domain.statistics.AppDto
import com.example.domain.statistics.GetCurrentScreenTimeUseCase
import com.example.domain.statistics.GetFavouriteAppsUseCase
import com.example.julie.HomeScreenState
import com.example.julie.Lce
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

// Only real time values!
data class HomeScreenContent(
    val currentScreenTime: Long = 0L,
    val threshold: Long = 0L,
    val points: Int = 0,
    val streakValue: Int = 0,
)

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val getCurrentScreenTimeUseCase: GetCurrentScreenTimeUseCase,
    private val getFavouriteAppsUseCase: GetFavouriteAppsUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _homeScreenState = MutableStateFlow<HomeScreenState>(value = Lce.Loading)

    val homeScreenState = _homeScreenState.asStateFlow()

    fun getCurrentScreenTime() {
        _homeScreenState.update {
            Lce.Content(HomeScreenContent(currentScreenTime = getCurrentScreenTimeUseCase()))
        }
    }

    suspend fun getFavouriteApps(): List<AppDto> =
        withContext(ioDispatcher) { getFavouriteAppsUseCase().fold({ emptyList() }, { it }) }
}
