package com.example.julie.smartphoneusage

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.SwipeableCardState
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.example.domain.report.ReportDto
import com.example.julie.Lce
import com.example.julie.R
import com.example.julie.components.AppMessage
import com.example.julie.components.AppReactions
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation
import com.example.julie.ui.theme.textColor
import java.time.LocalDate

@Composable
internal fun SmartphoneUsageScreen(
    modifier: Modifier,
    smartphoneUsageViewModel: SmartphoneUsageViewModel,
    paddingValues: PaddingValues,
    onStoryOpen: () -> Unit
) {
    val state by smartphoneUsageViewModel.smartphoneUsageScreenState.collectAsState()

    val currentAppsStatsState by smartphoneUsageViewModel.currentAppsStatsState.collectAsState()

    LaunchedEffect(key1 = Unit) { smartphoneUsageViewModel.getCurrentAppsStats() }

    LaunchedEffect(key1 = Unit) { smartphoneUsageViewModel.getUserReports() }

    Column(
        modifier = modifier.fillMaxSize().padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val currentState = state) {
            is Lce.Loading -> {
                CircularProgressIndicator(
                    modifier = modifier.width(64.dp),
                    color = NeobrutalismTheme.colors.contentPrimary,
                    trackColor = NeobrutalismTheme.colors.background,
                )
            }
            is Lce.Content -> {
                val currentAppsStatsPair = currentAppsStatsState to rememberSwipeableCardState()
                val reports =
                    currentState.value.oldReports.map { it to rememberSwipeableCardState() }

                StoriesHeader(modifier = modifier, onStoryOpen = onStoryOpen)

                if (currentAppsStatsPair.second.swipedDirection == null) {
                    SwipeableAppUsage(
                        modifier = modifier,
                        swipeableCardState = currentAppsStatsPair.second,
                        reportDto = ReportDto(LocalDate.now(), currentAppsStatsPair.first)
                    )
                }

                reports.forEach { (report, swipeableState) ->
                    if (swipeableState.swipedDirection == null) {
                        SwipeableAppUsage(
                            modifier = modifier,
                            swipeableCardState = swipeableState,
                            reportDto = report
                        )
                    }
                }
            }
            is Lce.Failure -> {}
        }
    }
}

@Composable
internal fun StoriesHeader(modifier: Modifier, onStoryOpen: () -> Unit) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(NeobrutalismTheme.colors.contentSecondary)
                .drawBehind {
                    val strokeWidth = 6f
                    val y = size.height - strokeWidth / 2
                    drawLine(textColor, Offset(0f, 0f), Offset(size.width, 0f), strokeWidth)
                    drawLine(textColor, Offset(0f, y), Offset(size.width, y), strokeWidth)
                }
                .horizontalScroll(rememberScrollState())
    ) {
        Button(onClick = onStoryOpen) { Text(text = "Test story") }
    }
}

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
internal fun SwipeableAppUsage(
    modifier: Modifier,
    swipeableCardState: SwipeableCardState,
    reportDto: ReportDto
) {
    if (reportDto.appReports.isEmpty()) {
        Text(text = "No reports to show")
    }

    Box(
        modifier =
            modifier.swipableCard(
                state = swipeableCardState,
                blockedDirections = listOf(Direction.Down, Direction.Up),
                onSwiped = {},
                onSwipeCancel = {}
            )
    ) {
        Box(
            modifier =
                modifier
                    .padding(vertical = 16.dp)
                    .neubrutalismElevation(cornersRadius = 0.dp, borderWidth = 4.dp)
                    .fillMaxWidth(.9f)
                    .fillMaxHeight()
                    .background(Color.White)
        ) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier =
                        modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .background(NeobrutalismTheme.colors.buttonSecondary)
                            .drawBehind {
                                val strokeWidth = 6f
                                val y = size.height - strokeWidth / 2
                                drawLine(
                                    textColor,
                                    Offset(0f, y),
                                    Offset(size.width, y),
                                    strokeWidth
                                )
                            },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "App Usage",
                        style =
                            TextStyle(
                                fontSize = 36.sp,
                                fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
                                color = textColor,
                            ),
                        modifier = modifier.fillMaxWidth(.5f).padding(horizontal = 16.dp)
                    )

                    Text(
                        text = reportDto.date.toString(),
                        style =
                            TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily(Font(R.font.inconsolata_variable)),
                                color = textColor.copy(alpha = .5f),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            ),
                        modifier = modifier.fillMaxWidth()
                    )
                }

                Column(
                    modifier =
                        modifier.padding(vertical = 16.dp).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top,
                ) {
                    reportDto.appReports
                        .sortedByDescending { it.screenTime }
                        .forEach { appDto ->
                            AppMessage(modifier = modifier, appDto = appDto)
                            AppReactions(modifier = modifier, appDto = appDto)
                        }
                }
            }
        }
    }
}
