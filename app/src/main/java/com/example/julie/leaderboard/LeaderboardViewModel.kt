package com.example.julie.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.user.GetLeaderboardUseCase
import com.example.domain.user.LeaderboardItem
import com.example.julie.Lce
import com.example.julie.LeaderboardScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal data class LeaderboardScreenContent(val leaderboard: List<LeaderboardItem>)

@HiltViewModel
internal class LeaderboardViewModel
@Inject
constructor(private val getLeaderboardUseCase: GetLeaderboardUseCase) : ViewModel() {
    private val _leaderboardScreenState =
        MutableStateFlow<LeaderboardScreenState>(value = Lce.Loading)

    val leaderboardScreenState = _leaderboardScreenState.asStateFlow()

    fun getLeaderboard() =
        viewModelScope.launch {
            getLeaderboardUseCase()
                .onLeft { error -> _leaderboardScreenState.update { Lce.Failure(error) } }
                .onRight { items ->
                    _leaderboardScreenState.update { Lce.Content(LeaderboardScreenContent(items)) }
                }
        }
}
