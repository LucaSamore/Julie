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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.example.julie.R
import com.example.julie.components.AppMessage
import com.example.julie.components.AppReactions
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation
import com.example.julie.ui.theme.textColor

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
internal fun SmartphoneUsageScreen(
    modifier: Modifier,
    smartphoneUsageViewModel: SmartphoneUsageViewModel,
    paddingValues: PaddingValues
) {
    val state by smartphoneUsageViewModel.smartphoneUsageScreenState.collectAsState()

    val swipeableCardState = rememberSwipeableCardState()

    Column(
        modifier = modifier.fillMaxSize().padding(paddingValues),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        ) {}

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
                            text = "27 Feb 2024",
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
                            modifier
                                .padding(vertical = 16.dp)
                                .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Top,
                    ) {
                        AppMessage(modifier = modifier)

                        AppReactions(modifier = modifier)
                    }
                }
            }
        }
    }
}
