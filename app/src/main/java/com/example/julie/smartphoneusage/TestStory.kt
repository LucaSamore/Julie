package com.example.julie.smartphoneusage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.julie.ui.theme.NeobrutalismTheme
import com.ui.simplestories.Stories

@Composable
internal fun TestStory(
    modifier: Modifier,
    paddingValues: PaddingValues,
    onStoryEnd: () -> Unit,
) {
    Stories(
        numberOfPages = 1,
        onComplete = onStoryEnd,
        indicatorProgressColor = NeobrutalismTheme.colors.text,
        indicatorBackgroundGradientColors = listOf(Color.Transparent, Color.Transparent),
    ) {
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(NeobrutalismTheme.colors.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "This is a story")
        }
    }
}
