package com.example.julie.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.domain.statistics.AppDto
import com.example.julie.Lce
import com.example.julie.R
import com.example.julie.components.NeubrutalMusicBox
import com.example.julie.components.NeubrutalPointsStreakBox
import com.example.julie.components.NeubrutalVolumeBox
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation
import com.example.julie.ui.theme.textColor

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier,
    homeViewModel: HomeViewModel,
    paddingValues: PaddingValues
) {
    val homeScreenState by homeViewModel.homeScreenState.collectAsState()
    val screenTimeState by homeViewModel.screenTimeState.collectAsState()

    homeViewModel.getCurrentScreenTime()

    var currentScreenTime by rememberSaveable { mutableLongStateOf(0L) }
    var threshold by rememberSaveable { mutableLongStateOf(0L) }
    var points by rememberSaveable { mutableIntStateOf(0) }
    var streak by rememberSaveable { mutableIntStateOf(0) }
    val favouriteApps = remember { mutableStateListOf<AppDto>() }

    var screenTimeSliderPosition by remember { mutableFloatStateOf(.0f) }
    var thresholdSliderPosition by remember { mutableFloatStateOf(.0f) }

    var errorMessage by rememberSaveable { mutableStateOf("") }
    var errorMessageHidden by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit) { homeViewModel.getContent() }

    Column(
        modifier =
            modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val currentScreenTimeState = screenTimeState
        currentScreenTime = currentScreenTimeState
        screenTimeSliderPosition = (currentScreenTime.toFloat() / (24 * 60 * 60 * 1000))

        NeubrutalMusicBox(
            modifier = modifier,
            currentScreenTime = currentScreenTime,
            screenTimeSliderPosition = screenTimeSliderPosition
        )

        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!errorMessageHidden) {
                Text(
                    text = errorMessage,
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
                return
            }

            NeubrutalVolumeBox(
                modifier = modifier,
                thresholdValue = threshold,
                thresholdSliderPosition = thresholdSliderPosition
            )

            NeubrutalPointsStreakBox(modifier = modifier, points = points, streak = streak)
        }

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
                text = "Your favourite apps",
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

        Box(
            modifier =
                modifier
                    .fillMaxWidth(.9f)
                    .fillMaxHeight(.5f)
                    .padding(vertical = 16.dp)
                    .neubrutalismElevation()
                    .background(color = NeobrutalismTheme.colors.contentPrimary)
        ) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                favouriteApps.forEachIndexed { index, app ->
                    Row(
                        modifier = modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = app.icon),
                            "${favouriteApps.first().appName} Icon",
                            modifier = modifier.size(48.dp, 48.dp)
                        )

                        Text(
                            text = "#${index + 1}",
                            style =
                                TextStyle(
                                    fontSize = 28.sp,
                                    textAlign = TextAlign.Start,
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
                                    fontWeight = FontWeight.Bold,
                                    color = NeobrutalismTheme.colors.background,
                                ),
                            modifier = modifier.fillMaxWidth(.2f)
                        )

                        Text(
                            text = app.appName,
                            style =
                                TextStyle(
                                    fontSize = 28.sp,
                                    textAlign = TextAlign.Start,
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
                                    fontWeight = FontWeight.Bold,
                                    color = NeobrutalismTheme.colors.background,
                                ),
                            modifier = modifier.fillMaxWidth(.6f)
                        )
                    }
                }
            }
        }
    }

    when (val currentState = homeScreenState) {
        is Lce.Loading -> errorMessageHidden = true
        is Lce.Content -> {
            threshold = currentState.value.threshold
            thresholdSliderPosition = screenTimeState.toFloat() / threshold
            points = currentState.value.points
            streak = currentState.value.streakValue
            favouriteApps.apply {
                clear()
                addAll(currentState.value.favouriteApps)
            }
        }
        is Lce.Failure -> {
            errorMessage = currentState.error.message
            errorMessageHidden = false
        }
    }
}
