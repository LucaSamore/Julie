package com.example.julie.smartphoneusage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.domain.report.AppDto
import com.example.domain.report.ReportDto
import com.example.julie.Lce
import com.example.julie.R
import com.example.julie.bounceClick
import com.example.julie.components.AppMessage
import com.example.julie.components.AppReactions
import com.example.julie.navigation.Destination
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation
import com.example.julie.ui.theme.textColor
import com.github.theapache64.twyper.flip.TwyperFlip
import com.github.theapache64.twyper.flip.rememberTwyperFlipController
import java.time.LocalDate

@Composable
internal fun SmartphoneUsageScreen(
    modifier: Modifier,
    smartphoneUsageViewModel: SmartphoneUsageViewModel,
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    val usageState by smartphoneUsageViewModel.smartphoneUsageScreenState.collectAsState()
    val currentUsageState by smartphoneUsageViewModel.currentAppsStatsState.collectAsState()

    val twyperFlipController = rememberTwyperFlipController()
    val reports = remember { mutableStateListOf<ReportDto>() }

    LaunchedEffect(key1 = Unit) { smartphoneUsageViewModel.getCurrentAppsStats() }

    LaunchedEffect(key1 = Unit) { smartphoneUsageViewModel.getUserReports() }

    Column(
        modifier = modifier.fillMaxSize().padding(paddingValues),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val currentState = usageState) {
            is Lce.Loading -> {
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = modifier.width(64.dp),
                        color = NeobrutalismTheme.colors.contentPrimary,
                        trackColor = NeobrutalismTheme.colors.background,
                    )
                }
            }
            is Lce.Content -> {
                reports.apply {
                    clear()
                    add(currentUsageState)
                    addAll(currentState.value.oldReports.reversed())
                }

                StoriesHeader(
                    modifier = modifier,
                    stories = getStories(currentState.value.oldReports),
                    navController = navController
                )

                TwyperFlip(
                    items = reports,
                    twyperFlipController = twyperFlipController,
                    onItemRemoved = { item, _ -> reports.remove(item) },
                    cardModifier = { modifier },
                    stackCount = 2,
                    paddingBetweenCards = 0f,
                    modifier =
                        modifier.padding(vertical = 32.dp).bounceClick().clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            twyperFlipController.flip()
                        },
                    front = { SwipeableAppUsage(modifier = modifier, reportDto = it) },
                    back = { /* TODO */}
                )

                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "No more reports")
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
internal fun StoriesHeader(
    modifier: Modifier,
    stories: List<List<AppDto>>,
    navController: NavHostController
) {
    if (stories.isEmpty()) {
        return
    }

    val show = stories.first().filter { it.screenTime > 0 }

    if (show.isEmpty()) {
        return
    }

    LazyRow(
        modifier =
            modifier
                .fillMaxWidth()
                .background(NeobrutalismTheme.colors.contentSecondary)
                .drawBehind {
                    val strokeWidth = 6f
                    val y = size.height - strokeWidth / 2
                    drawLine(textColor, Offset(0f, 0f), Offset(size.width, 0f), strokeWidth + 6f)
                    drawLine(textColor, Offset(0f, y), Offset(size.width, y), strokeWidth)
                }
                .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(show) {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        navController.navigate("${Destination.Story.name}/${it.name}") {
                            popUpTo("${Destination.Story.name}/${it.name}") { inclusive = true }
                        }
                    },
                    modifier = modifier.padding(horizontal = 6.dp).bounceClick()
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = it.icon),
                        "${it.name} Icon",
                        contentScale = ContentScale.Crop,
                        modifier = modifier.size(48.dp, 48.dp)
                    )
                }
                Text(
                    text = it.name,
                    style =
                        TextStyle(
                            fontSize = 12.sp,
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
                            color = NeobrutalismTheme.colors.text
                        )
                )
            }
        }
    }
}

@Composable
internal fun SwipeableAppUsage(modifier: Modifier, reportDto: ReportDto) {
    Box(
        modifier =
            modifier
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
                            drawLine(textColor, Offset(0f, y), Offset(size.width, y), strokeWidth)
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
                modifier = modifier.padding(vertical = 16.dp).verticalScroll(rememberScrollState()),
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

private fun getStories(oldReports: Iterable<ReportDto>): List<List<AppDto>> {
    return oldReports
        .asSequence()
        .sortedByDescending { it.date }
        .take(2)
        .filter { it.date.isAfter(LocalDate.now().minusDays(4)) }
        .map { it.appReports }
        .toList()
}
