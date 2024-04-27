package com.example.julie.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.julie.R
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation
import java.time.LocalDate
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun NeubrutalMusicBox(
    modifier: Modifier,
    currentScreenTime: Long,
    screenTimeSliderPosition: Float
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
                            imageVector = ImageVector.vectorResource(R.drawable.baseline_pause_24),
                            contentDescription = "Pause",
                            tint = NeobrutalismTheme.colors.text,
                            modifier = modifier.size(32.dp, 32.dp)
                        )
                    }

                    Spacer(modifier = modifier.padding(horizontal = 16.dp))

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_skip_next_24),
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
                            currentScreenTime.milliseconds.toComponents { hh, mm, ss, _ ->
                                "${hh}h ${mm}min ${ss}sec"
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
}
