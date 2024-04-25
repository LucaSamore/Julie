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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.julie.R
import com.example.julie.components.NeubrutalContentPrimaryText
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier,
    homeViewModel: HomeViewModel,
    paddingValues: PaddingValues
) {
    var screenTimeSliderPosition by remember { mutableFloatStateOf(.20f) }

    var thresholdSliderPosition by remember { mutableFloatStateOf(.65f) }

    Column(
        modifier = modifier.fillMaxSize().padding(paddingValues),
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
                        content = "29 Mar 2024",
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
                            content = "1h 34min",
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
    }
}
