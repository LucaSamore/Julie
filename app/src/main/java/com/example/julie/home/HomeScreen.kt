package com.example.julie.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.julie.Lce
import com.example.julie.R
import com.example.julie.components.LoadingBar
import com.example.julie.components.RecentlyPlayedContent
import com.example.julie.components.RecentlyPlayedHeader
import com.example.julie.components.neubrutalism.NeubrutalMusicBox
import com.example.julie.components.neubrutalism.NeubrutalPointsStreakBox
import com.example.julie.components.neubrutalism.NeubrutalVolumeBox
import com.example.julie.ui.theme.NeobrutalismTheme

@Composable
internal fun HomeScreen(
    modifier: Modifier,
    homeViewModel: HomeViewModel,
    paddingValues: PaddingValues
) {
    val homeScreenState by homeViewModel.homeScreenState.collectAsState()
    val screenTimeState by homeViewModel.screenTimeState.collectAsState()

    homeViewModel.getCurrentScreenTime()

    LaunchedEffect(key1 = Unit) { homeViewModel.getContent() }

    Column(
        modifier =
            modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val currentState = homeScreenState) {
            is Lce.Loading -> {
                LoadingBar(modifier)
            }
            is Lce.Content -> {
                NeubrutalMusicBox(
                    modifier = modifier,
                    currentScreenTime = screenTimeState,
                    screenTimeSliderPosition = (screenTimeState.toFloat() / (24 * 60 * 60 * 1000))
                )

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NeubrutalVolumeBox(
                        modifier = modifier,
                        thresholdValue = currentState.value.threshold,
                        thresholdSliderPosition =
                            screenTimeState.toFloat() / currentState.value.threshold
                    )

                    NeubrutalPointsStreakBox(
                        modifier = modifier,
                        points = currentState.value.points,
                        streak = currentState.value.streakValue
                    )
                }

                if (currentState.value.favouriteApps.isNotEmpty()) {
                    RecentlyPlayedHeader(modifier = modifier)
                    RecentlyPlayedContent(
                        modifier = modifier,
                        favouriteApps = currentState.value.favouriteApps
                    )
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
