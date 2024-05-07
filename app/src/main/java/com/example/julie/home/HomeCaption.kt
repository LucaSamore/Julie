package com.example.julie.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.julie.R
import com.example.julie.components.NeubrutalMusicBox
import com.example.julie.components.NeubrutalPointsStreakBox
import com.example.julie.components.NeubrutalVolumeBox
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.textColor

@Composable
internal fun HomeCaption(modifier: Modifier, paddingValues: PaddingValues) {
    Column(
        modifier =
            modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NeubrutalMusicBox(
            modifier = modifier,
            currentScreenTime = CAPTION_ST,
            screenTimeSliderPosition = CAPTION_ST_SLIDER_POSITION
        )

        Text(
            text =
                "Musix box is a real time representation of your screen time. " +
                    "Slider moves as your screen time increases.",
            style =
                TextStyle(
                    fontFamily = FontFamily(Font(R.font.inconsolata_variable)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Justify,
                    color = NeobrutalismTheme.colors.text,
                ),
            modifier = modifier.fillMaxWidth(.9f).padding(vertical = 12.dp)
        )

        NeubrutalVolumeBox(
            modifier = modifier,
            thresholdValue = CAPTION_TH,
            thresholdSliderPosition = CAPTION_TH_SLIDER_POSITION
        )

        Text(
            text =
                "Volume slider is a real time representation of how close your screen time" +
                    " is to the threshold value. Slider moves as your screen time increases.",
            style =
                TextStyle(
                    fontFamily = FontFamily(Font(R.font.inconsolata_variable)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Justify,
                    color = NeobrutalismTheme.colors.text,
                ),
            modifier = modifier.fillMaxWidth(.9f).padding(vertical = 16.dp)
        )

        NeubrutalPointsStreakBox(
            modifier = modifier,
            points = 1644,
            streak = 4,
            verticalPadding = 16.dp
        )

        Text(
            text =
                "Left box contains the amount of points gained until today. " +
                    "Points get updated every night at 23:55" +
                    "Right box contains the streak number. The value is increased if at the " +
                    "end of the day your screen time is lower than the current threshold." +
                    "If your screen time is higher than the threshold, " +
                    "you lose all your points and your current streak.",
            style =
                TextStyle(
                    fontFamily = FontFamily(Font(R.font.inconsolata_variable)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Justify,
                    color = NeobrutalismTheme.colors.text,
                ),
            modifier = modifier.fillMaxWidth(.9f).padding(vertical = 16.dp)
        )

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

        Text(
            text =
                "The recently played section contains a 'playlist' of your most used apps " +
                    "over the last 7 days.",
            style =
                TextStyle(
                    fontFamily = FontFamily(Font(R.font.inconsolata_variable)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Justify,
                    color = NeobrutalismTheme.colors.text,
                ),
            modifier = modifier.fillMaxWidth(.9f).padding(vertical = 16.dp)
        )
    }
}

private const val CAPTION_ST = 10637392L

private const val CAPTION_TH = 18000000L

private const val CAPTION_ST_SLIDER_POSITION = (CAPTION_ST.toFloat() / (24 * 60 * 60 * 1000))

private const val CAPTION_TH_SLIDER_POSITION = (CAPTION_ST.toFloat() / CAPTION_TH)
