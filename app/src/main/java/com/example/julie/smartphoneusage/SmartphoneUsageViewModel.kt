package com.example.julie.smartphoneusage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.report.AppDto
import com.example.domain.report.GetUserReportsUseCase
import com.example.domain.report.ReportDto
import com.example.domain.statistics.GetAppsCurrentStatsUseCase
import com.example.julie.Lce
import com.example.julie.SmartphoneUsageScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SmartphoneUsageScreenContent(
    val oldReports: Iterable<ReportDto>,
)

@HiltViewModel
internal class SmartphoneUsageViewModel
@Inject
constructor(
    private val getUserReportsUseCase: GetUserReportsUseCase,
    private val getAppsCurrentStatsUseCase: GetAppsCurrentStatsUseCase,
) : ViewModel() {

    private val _smartphoneUsageScreenState =
        MutableStateFlow<SmartphoneUsageScreenState>(value = Lce.Loading)

    private val _currentAppsStatsState = MutableStateFlow<List<AppDto>>(value = emptyList())

    val smartphoneUsageScreenState = _smartphoneUsageScreenState.asStateFlow()

    val currentAppsStatsState = _currentAppsStatsState.asStateFlow()

    fun getUserReports() =
        viewModelScope.launch {
            getUserReportsUseCase()
                .onLeft { error -> _smartphoneUsageScreenState.update { Lce.Failure(error) } }
                .onRight { reports ->
                    _smartphoneUsageScreenState.update {
                        Lce.Content(SmartphoneUsageScreenContent(reports))
                    }
                }
        }

    fun getCurrentAppsStats() =
        viewModelScope.launch { _currentAppsStatsState.update { getAppsCurrentStatsUseCase() } }
}
