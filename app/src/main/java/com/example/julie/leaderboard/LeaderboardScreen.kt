package com.example.julie.leaderboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.julie.R
import com.example.julie.ui.theme.NeobrutalismTheme

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun LeaderboardScreen(
    modifier: Modifier,
    leaderboardViewModel: LeaderboardViewModel,
    paddingValues: PaddingValues,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "This feature has not been implemented yet",
            style =
                TextStyle(
                    fontSize = 28.sp,
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
                    color = NeobrutalismTheme.colors.text,
                )
        )
    }
}
