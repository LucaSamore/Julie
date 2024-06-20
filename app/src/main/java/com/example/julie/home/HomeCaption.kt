package com.example.julie.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.julie.R
import com.example.julie.components.RecentlyPlayedHeader
import com.example.julie.components.neubrutalism.NeubrutalMusicBox
import com.example.julie.components.neubrutalism.NeubrutalPointsStreakBox
import com.example.julie.components.neubrutalism.NeubrutalVolumeBox
import com.example.julie.ui.theme.NeobrutalismTheme

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

        CaptionDescription(
            modifier = modifier,
            text =
                "The music box is a real-time representation of your screen time. " +
                    "Slider moves as your screen time increases."
        )

        NeubrutalVolumeBox(
            modifier = modifier,
            thresholdValue = CAPTION_TH,
            thresholdSliderPosition = CAPTION_TH_SLIDER_POSITION,
            verticalPadding = 16.dp
        )

        CaptionDescription(
            modifier = modifier,
            text =
                "Volume slider is a real-time representation of how close your screen time" +
                    " is to the threshold value. Slider moves as your screen time increases."
        )

        NeubrutalPointsStreakBox(
            modifier = modifier,
            points = 1644,
            streak = 4,
            verticalPadding = 16.dp
        )

        CaptionDescription(
            modifier = modifier,
            text =
                "Left box contains the amount of points gained until today. " +
                    "Points are updated every night at 23:55 " +
                    "Right box contains the streak number. The value is increased if, at the " +
                    "end of the day, your screen time is lower than the current threshold. " +
                    "If your screen time is higher than the threshold, " +
                    "you lose all your points and your current streak."
        )

        RecentlyPlayedHeader(modifier = modifier, verticalPadding = 16.dp)

        CaptionDescription(
            modifier = modifier,
            text =
                "The recently played section contains a 'playlist' of your most used apps " +
                    "over the last 7 days. Average time use is also provided."
        )
    }
}

@Composable
private fun CaptionDescription(modifier: Modifier, text: String) {
    Text(
        text = text,
        style =
            TextStyle(
                fontFamily = FontFamily(Font(R.font.inconsolata_variable)),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Justify,
                color = NeobrutalismTheme.colors.text,
            ),
        modifier = modifier.fillMaxWidth(.9f).padding(vertical = 8.dp)
    )
}

private const val CAPTION_ST = 10637392L

private const val CAPTION_TH = 18000000L

private const val CAPTION_ST_SLIDER_POSITION = (CAPTION_ST.toFloat() / (24 * 60 * 60 * 1000))

private const val CAPTION_TH_SLIDER_POSITION = (CAPTION_ST.toFloat() / CAPTION_TH)
