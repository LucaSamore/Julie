package com.example.julie.smartphoneusage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.statistics.AppComparisonUseCase
import com.example.domain.statistics.implementation.ComparisonReportDto
import com.example.julie.Lce
import com.example.julie.StoryScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class StoryContent(
    val mostRecent: ComparisonReportDto,
    val secondMostRecent: ComparisonReportDto
)

@HiltViewModel
internal class StoryViewModel
@Inject
constructor(private val appComparisonUseCase: AppComparisonUseCase) : ViewModel() {

    private val _appComparisonState = MutableStateFlow<StoryScreenState>(value = Lce.Loading)

    val appComparisonState = _appComparisonState.asStateFlow()

    fun getAppComparison(appName: String) =
        viewModelScope.launch {
            appComparisonUseCase(appName)
                .onLeft { error -> _appComparisonState.update { Lce.Failure(error) } }
                .onRight { dtos ->
                    _appComparisonState.update {
                        Lce.Content(StoryContent(dtos.first, dtos.second))
                    }
                }
        }
}
