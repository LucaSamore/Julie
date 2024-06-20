package com.example.julie.components.neubrutalism

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.julie.R
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun NeubrutalVolumeBox(
    modifier: Modifier,
    thresholdValue: Long,
    thresholdSliderPosition: Float,
    verticalPadding: Dp = 0.dp
) {
    Row(modifier = modifier.fillMaxWidth(.9f).padding(vertical = verticalPadding)) {
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
                    text =
                        thresholdValue.milliseconds.toComponents { hh, mm, _, _ ->
                            "${hh}h ${mm}min"
                        },
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
                    modifier = modifier.padding(horizontal = 6.dp)
                )
            }
        }
    }
}
