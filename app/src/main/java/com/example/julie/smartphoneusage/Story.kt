package com.example.julie.smartphoneusage

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.domain.statistics.implementation.ComparisonReportDto
import com.example.julie.Lce
import com.example.julie.R
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.textColor
import com.ui.simplestories.Stories

@Composable
internal fun Story(
    appName: String,
    modifier: Modifier,
    storyViewModel: StoryViewModel,
    onStoryEnd: () -> Unit,
) {

    val state by storyViewModel.appComparisonState.collectAsState()

    LaunchedEffect(key1 = Unit) { storyViewModel.getAppComparison(appName) }

    when (val currentState = state) {
        is Lce.Loading -> {
            CircularProgressIndicator(
                modifier = modifier.width(64.dp),
                color = NeobrutalismTheme.colors.contentPrimary,
                trackColor = NeobrutalismTheme.colors.background,
            )
        }
        is Lce.Content -> {
            Stories(
                numberOfPages = 1,
                onComplete = onStoryEnd,
                indicatorProgressColor = NeobrutalismTheme.colors.text,
                indicatorBackgroundGradientColors = listOf(Color.Transparent, Color.Transparent),
            ) {
                Column(
                    modifier =
                        modifier
                            .padding(vertical = 42.dp)
                            .fillMaxSize()
                            .background(NeobrutalismTheme.colors.background),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    StoryHeader(
                        modifier = modifier,
                        appName = appName,
                        appIcon = currentState.value.mostRecent.app.icon
                    )

                    StoryContent(
                        modifier = modifier,
                        mostRecentApp = currentState.value.mostRecent,
                        secondMostRecent = currentState.value.secondMostRecent
                    )
                }
            }
        }
        is Lce.Failure -> {}
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun StoryHeader(modifier: Modifier, appName: String, appIcon: Drawable?) {
    Row(
        modifier = modifier.padding(horizontal = 12.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = appIcon),
            "$appName Icon",
            contentScale = ContentScale.Crop,
            modifier = modifier.size(36.dp, 36.dp)
        )

        Text(
            text = "@${appName}",
            style =
                TextStyle(
                    fontSize = 16.sp,
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
                    fontWeight = FontWeight.SemiBold,
                    color = NeobrutalismTheme.colors.text
                ),
            modifier = modifier.padding(horizontal = 6.dp)
        )

        Spacer(modifier = modifier.padding(horizontal = 4.dp))

        Text(text = "23h", style = TextStyle(color = textColor.copy(alpha = .5f)))
    }
}

@Composable
private fun StoryContent(
    modifier: Modifier,
    mostRecentApp: ComparisonReportDto,
    secondMostRecent: ComparisonReportDto
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "This is a story for ${mostRecentApp.app.name}")
    }
}
