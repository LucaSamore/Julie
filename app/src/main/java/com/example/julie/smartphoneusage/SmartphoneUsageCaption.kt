package com.example.julie.smartphoneusage

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.report.AppDto
import com.example.domain.report.ReportDto
import com.example.julie.R
import com.example.julie.bounceClick
import com.example.julie.components.AppMessage
import com.example.julie.components.AppReactions
import com.example.julie.components.CaptionDescription
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation
import com.example.julie.ui.theme.textColor
import java.time.LocalDate

@Composable
internal fun SmartphoneUsageCaption(modifier: Modifier, paddingValues: PaddingValues) {
    Column(
        modifier =
            modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        StaticStoriesHeader(modifier)

        CaptionDescription(
            modifier = modifier.padding(vertical = 16.dp),
            text =
                "Click on the app icon to open a story. " +
                    "Each story contains a comparison of app usage over the past two days."
        )

        StaticAppUsageSwipeableCard(modifier = modifier, reportDto = fakeReport)

        CaptionDescription(
            modifier = modifier.padding(vertical = 16.dp),
            text =
                "The above card provides detailed information about smartphone usage for a specific day. Each message originates from an app and includes the total screen time recorded.\n" +
                    "Each message can have up to two reactions:\n" +
                    "1) The number of hearts indicates how many times the app has been opened.\n" +
                    "2) The number of messages indicates how many notifications you received from that app.\n" +
                    "Swipe left or right to navigate through past reports."
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun StaticStoriesHeader(modifier: Modifier) {
    Row(
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
        repeat(3) {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {},
                    modifier = modifier.padding(horizontal = 6.dp).bounceClick()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.default_icon),
                        "Icon",
                        contentScale = ContentScale.Crop,
                        modifier = modifier.size(48.dp, 48.dp)
                    )
                }
                Text(
                    text = "App",
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
internal fun StaticAppUsageSwipeableCard(modifier: Modifier, reportDto: ReportDto) {
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
                modifier = modifier.padding(vertical = 16.dp),
                verticalArrangement = Arrangement.Top,
            ) {
                reportDto.appReports
                    .sortedByDescending { it.screenTime }
                    .forEach {
                        AppMessage(modifier = modifier, appDto = it)
                        AppReactions(modifier = modifier, appDto = it)
                    }
            }
        }
    }
}

private val fakeReport =
    ReportDto(
        date = LocalDate.now(),
        appReports =
            listOf(
                AppDto(
                    name = "App 1",
                    icon = null,
                    screenTime = 6_000_000L,
                    notificationsReceived = 14,
                    timesOpened = 45
                ),
                AppDto(
                    name = "App 2",
                    icon = null,
                    screenTime = 400_000L,
                    notificationsReceived = 90,
                    timesOpened = 32
                ),
                AppDto(
                    name = "App 3",
                    icon = null,
                    screenTime = 8_000_000L,
                    notificationsReceived = 45,
                    timesOpened = 12
                )
            )
    )
