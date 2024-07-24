package com.example.julie.leaderboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.julie.Lce
import com.example.julie.R
import com.example.julie.components.LoadingBar
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation

@Composable
internal fun LeaderboardScreen(
    modifier: Modifier,
    leaderboardViewModel: LeaderboardViewModel,
    paddingValues: PaddingValues,
) {
    val leaderboardState by leaderboardViewModel.leaderboardScreenState.collectAsState()

    leaderboardViewModel.getLeaderboard()

    Column(
        modifier = modifier.fillMaxSize().padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val currentState = leaderboardState) {
            is Lce.Loading -> {
                LoadingBar(modifier = modifier)
            }
            is Lce.Content -> {
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    itemsIndexed(currentState.value.leaderboard) { index, item ->
                        Row(
                            modifier =
                                modifier
                                    .fillMaxWidth(.9f)
                                    .height(96.dp)
                                    .padding(vertical = 16.dp)
                                    .neubrutalismElevation(backgroundColor = Color.White),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            LeaderboardItemText(
                                modifier = modifier,
                                text = "#${index + 1}",
                                fontSize = 36.sp
                            )
                            LeaderboardItemText(modifier = modifier, text = item.username)
                            LeaderboardItemText(modifier = modifier, text = item.points.toString())
                        }
                    }
                }
            }
            is Lce.Failure -> {
                Text(
                    text = currentState.error.message,
                    style =
                        TextStyle(
                            fontFamily = FontFamily(Font(R.font.inconsolata_variable)),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = NeobrutalismTheme.colors.text,
                        ),
                    modifier = modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun LeaderboardItemText(
    modifier: Modifier,
    text: String,
    fontSize: TextUnit = 28.sp,
) {
    Text(
        text = text,
        style =
            TextStyle(
                fontSize = fontSize,
                fontFamily =
                    FontFamily(
                        Font(
                            R.font.bebas_neue_regular,
                            variationSettings =
                                FontVariation.Settings(
                                    FontVariation.weight(600),
                                )
                        )
                    ),
                fontWeight = FontWeight.Normal,
                color = NeobrutalismTheme.colors.text,
            ),
        modifier = modifier
    )
}
