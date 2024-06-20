package com.example.julie.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.rememberAsyncImagePainter
import com.example.domain.statistics.FavouriteAppDto
import com.example.julie.Lce
import com.example.julie.R
import com.example.julie.components.NeubrutalMusicBox
import com.example.julie.components.NeubrutalPointsStreakBox
import com.example.julie.components.NeubrutalVolumeBox
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.textColor
import kotlin.time.Duration.Companion.milliseconds

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
                CircularProgressIndicator(
                    modifier = modifier.width(64.dp),
                    color = NeobrutalismTheme.colors.contentPrimary,
                    trackColor = NeobrutalismTheme.colors.background,
                )
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

@Composable
private fun RecentlyPlayedHeader(modifier: Modifier) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(color = NeobrutalismTheme.colors.buttonSecondary)
                .drawBehind {
                    val strokeWidth = 6f
                    val y = size.height - strokeWidth / 2
                    drawLine(textColor, Offset(0f, 0f), Offset(size.width, 0f), strokeWidth)
                    drawLine(textColor, Offset(0f, y), Offset(size.width, y), strokeWidth)
                }
    ) {
        Text(
            text = "Recently played",
            style =
                TextStyle(
                    fontSize = 32.sp,
                    fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
                    textAlign = TextAlign.Center,
                    color = textColor,
                ),
            modifier = modifier.fillMaxWidth().padding(vertical = 12.dp)
        )
    }

    Row(
        modifier =
            modifier.fillMaxWidth().height(64.dp).background(color = Color.White).drawBehind {
                val strokeWidth = 6f
                val y = size.height - strokeWidth / 2
                drawLine(textColor, Offset(0f, y), Offset(size.width, y), strokeWidth)
            },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RecentlyPlayedText(modifier = modifier, text = "#", textAlign = TextAlign.Start)
        RecentlyPlayedText(
            modifier = modifier.width(48.dp),
            text = "Icon",
            textAlign = TextAlign.Center
        )
        RecentlyPlayedText(
            modifier = modifier.width(96.dp),
            text = "Title",
            textAlign = TextAlign.Start
        )
        RecentlyPlayedText(
            modifier = modifier.width(96.dp),
            text = "Played for",
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun RecentlyPlayedContent(modifier: Modifier, favouriteApps: List<FavouriteAppDto>) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        favouriteApps.forEachIndexed { index, app ->
            Row(
                modifier = modifier.fillMaxWidth().height(96.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RecentlyPlayedText(
                    modifier = modifier,
                    text = "${index + 1}",
                    fontSize = 32.sp,
                    textAlign = TextAlign.Start
                )

                Image(
                    painter = rememberAsyncImagePainter(model = app.icon),
                    "${app.appName} Icon",
                    contentScale = ContentScale.Crop,
                    modifier = modifier.size(48.dp, 48.dp)
                )

                RecentlyPlayedText(
                    modifier = modifier.width(96.dp),
                    text = app.appName,
                    textAlign = TextAlign.Start
                )

                RecentlyPlayedText(
                    modifier = modifier.width(96.dp),
                    text =
                        app.appScreenTime.milliseconds.toComponents { hh, mm, _, _ ->
                            "${hh}h ${mm}min"
                        },
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun RecentlyPlayedText(
    modifier: Modifier,
    text: String,
    fontSize: TextUnit = 18.sp,
    textAlign: TextAlign,
) {
    Text(
        text = text,
        style =
            TextStyle(
                fontSize = fontSize,
                textAlign = textAlign,
                fontFamily =
                    FontFamily(
                        Font(
                            R.font.nunito_variable,
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
