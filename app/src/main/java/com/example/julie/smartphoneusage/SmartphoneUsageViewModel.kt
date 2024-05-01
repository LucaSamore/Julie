package com.example.julie.smartphoneusage

import androidx.lifecycle.ViewModel
import com.example.domain.report.GetUserReportsUseCase
import com.example.domain.report.ReportDto
import com.example.julie.Lce
import com.example.julie.SmartphoneUsageScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SmartphoneUsageScreenContent(
    val currentStats: Unit,
    val oldReports: Iterable<ReportDto>,
)

@HiltViewModel
internal class SmartphoneUsageViewModel
@Inject
constructor(private val getUserReportsUseCase: GetUserReportsUseCase) : ViewModel() {

    private val _smartphoneUsageScreenState =
        MutableStateFlow<SmartphoneUsageScreenState>(value = Lce.Loading)
    val smartphoneUsageScreenState = _smartphoneUsageScreenState.asStateFlow()
}
