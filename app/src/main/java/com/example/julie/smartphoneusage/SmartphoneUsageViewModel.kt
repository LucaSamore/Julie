package com.example.julie.smartphoneusage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.report.GetUserReportsUseCase
import com.example.domain.report.ReportDto
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
constructor(private val getUserReportsUseCase: GetUserReportsUseCase) : ViewModel() {

    private val _smartphoneUsageScreenState =
        MutableStateFlow<SmartphoneUsageScreenState>(value = Lce.Loading)

    val smartphoneUsageScreenState = _smartphoneUsageScreenState.asStateFlow()

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
}
