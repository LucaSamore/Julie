package com.example.julie.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.julie.Lce
import com.example.julie.R
import com.example.julie.components.NeubrutalContentPrimaryText
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation
import com.example.julie.ui.theme.textColor
import java.time.LocalDate
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier,
    homeViewModel: HomeViewModel,
    paddingValues: PaddingValues
) {
    val state by homeViewModel.homeScreenState.collectAsState()

    homeViewModel.getCurrentScreenTime()

    var currentScreenTime by rememberSaveable { mutableLongStateOf(0L) }

    var screenTimeSliderPosition by remember { mutableFloatStateOf(.0f) }

    var thresholdSliderPosition by remember { mutableFloatStateOf(.0f) }

    Column(
        modifier =
            modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier =
                modifier
                    .fillMaxWidth(.9f)
                    .fillMaxHeight(.5f)
                    .padding(vertical = 16.dp)
                    .neubrutalismElevation(),
        ) {
            Column(
                modifier =
                    modifier
                        .fillMaxSize()
                        .background(NeobrutalismTheme.colors.contentPrimary)
                        .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column {
                    NeubrutalContentPrimaryText(
                        modifier = modifier.fillMaxWidth(.9f),
                        content = "Screen Time",
                        fontSize = 32.sp,
                        textAlign = TextAlign.Start
                    )

                    Spacer(modifier = modifier.padding(vertical = 8.dp))

                    NeubrutalContentPrimaryText(
                        modifier = modifier.fillMaxWidth(.9f),
                        content = LocalDate.now().toString(),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start
                    )
                }

                Column {
                    Row(
                        modifier = modifier.fillMaxWidth(.9f).padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector =
                                ImageVector.vectorResource(R.drawable.baseline_skip_previous_24),
                            contentDescription = "Previous",
                            tint = NeobrutalismTheme.colors.text,
                            modifier = modifier.size(32.dp, 32.dp)
                        )

                        Spacer(modifier = modifier.padding(horizontal = 16.dp))

                        Box(
                            modifier =
                                modifier
                                    .border(
                                        width = 2.dp,
                                        color = NeobrutalismTheme.colors.text,
                                        shape = CircleShape
                                    )
                                    .background(
                                        color = NeobrutalismTheme.colors.background,
                                        shape = CircleShape
                                    )
                                    .padding(6.dp)
                        ) {
                            Icon(
                                imageVector =
                                    ImageVector.vectorResource(R.drawable.baseline_pause_24),
                                contentDescription = "Pause",
                                tint = NeobrutalismTheme.colors.text,
                                modifier = modifier.size(32.dp, 32.dp)
                            )
                        }

                        Spacer(modifier = modifier.padding(horizontal = 16.dp))

                        Icon(
                            imageVector =
                                ImageVector.vectorResource(R.drawable.baseline_skip_next_24),
                            contentDescription = "Next",
                            tint = NeobrutalismTheme.colors.text,
                            modifier = modifier.size(32.dp, 32.dp)
                        )
                    }

                    Slider(
                        value = screenTimeSliderPosition,
                        colors =
                            SliderDefaults.colors(
                                thumbColor = NeobrutalismTheme.colors.text,
                                activeTrackColor = NeobrutalismTheme.colors.text,
                                inactiveTrackColor = Color(0xFF3E3E42),
                            ),
                        onValueChange = {},
                        modifier = modifier.fillMaxWidth(.9f)
                    )

                    Row(modifier = modifier.fillMaxWidth(.9f)) {
                        NeubrutalContentPrimaryText(
                            modifier = modifier.fillMaxWidth(.5f),
                            content =
                                currentScreenTime.milliseconds.toComponents { hh, mm, _, _ ->
                                    "${hh}h ${mm}min"
                                },
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start
                        )

                        NeubrutalContentPrimaryText(
                            modifier = modifier,
                            content = "24h",
                            fontSize = 16.sp,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }

        Row(modifier = modifier.fillMaxWidth(.9f)) {
            Box(
                modifier =
                    modifier
                        .neubrutalismElevation()
                        .height(48.dp)
                        .background(color = NeobrutalismTheme.colors.contentSecondary)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_volume_up_24),
                    contentDescription = "Volume",
                    tint = NeobrutalismTheme.colors.text,
                    modifier = modifier.size(48.dp, 48.dp).padding(6.dp)
                )
            }

            Spacer(modifier = modifier.padding(horizontal = 8.dp))

            Box(
                modifier =
                    modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .neubrutalismElevation()
                        .background(color = NeobrutalismTheme.colors.contentSecondary)
            ) {
                Row(
                    modifier = modifier.fillMaxSize().padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Slider(
                        value = thresholdSliderPosition,
                        colors =
                            SliderDefaults.colors(
                                thumbColor = NeobrutalismTheme.colors.text,
                                activeTrackColor = NeobrutalismTheme.colors.text,
                                inactiveTrackColor = Color(0xFF3E3E42),
                            ),
                        onValueChange = {},
                        modifier = modifier.fillMaxWidth(.7f)
                    )

                    Text(
                        text = "16h 42min",
                        style =
                            TextStyle(
                                fontFamily =
                                    FontFamily(
                                        Font(
                                            R.font.nunito_variable,
                                            variationSettings =
                                                FontVariation.Settings(
                                                    FontVariation.weight(800),
                                                )
                                        )
                                    ),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.End,
                                color = NeobrutalismTheme.colors.text
                            ),
                        modifier = modifier
                    )
                }
            }
        }

        Row(modifier = modifier.fillMaxWidth(.9f).padding(vertical = 32.dp)) {
            Box(
                modifier =
                    modifier
                        .fillMaxWidth(.5f)
                        .height(78.dp)
                        .neubrutalismElevation()
                        .background(color = NeobrutalismTheme.colors.buttonPrimary)
            ) {
                Column(
                    modifier = modifier.fillMaxSize().padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "3256",
                        style =
                            TextStyle(
                                fontSize = 28.sp,
                                fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
                                color = textColor,
                            )
                    )

                    Spacer(modifier = modifier.padding(vertical = 2.dp))

                    Text(
                        text = "Points",
                        style =
                            TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
                                color = textColor,
                            )
                    )
                }
            }

            Spacer(modifier = modifier.padding(horizontal = 8.dp))

            Box(
                modifier =
                    modifier
                        .fillMaxWidth()
                        .height(78.dp)
                        .neubrutalismElevation()
                        .background(color = NeobrutalismTheme.colors.buttonPrimary)
            ) {
                Column(
                    modifier = modifier.fillMaxSize().padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "4",
                        style =
                            TextStyle(
                                fontSize = 28.sp,
                                fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
                                color = textColor,
                            )
                    )

                    Spacer(modifier = modifier.padding(vertical = 2.dp))

                    Text(
                        text = "Current Streak",
                        style =
                            TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
                                color = textColor,
                            )
                    )
                }
            }
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
                Row(
                    modifier = modifier.fillMaxWidth(.9f).padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "#1",
                        style =
                            TextStyle(
                                fontSize = 32.sp,
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
                            )
                    )

                    Text(
                        text = "Instagram",
                        style =
                            TextStyle(
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
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
                            )
                    )
                }
            }
        }
    }

    when (val currentState = state) {
        is Lce.Loading -> {}
        is Lce.Content -> {
            currentScreenTime = currentState.value.currentScreenTime
            screenTimeSliderPosition = (currentScreenTime.toFloat() / (24 * 60 * 60 * 1000))
            thresholdSliderPosition = screenTimeSliderPosition
        }
        is Lce.Failure -> {}
    }
}
