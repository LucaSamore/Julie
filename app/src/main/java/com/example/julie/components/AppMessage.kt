package com.example.julie.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.domain.report.AppDto
import com.example.julie.R
import com.example.julie.ui.theme.NeobrutalismTheme
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun AppMessage(
    modifier: Modifier,
    appDto: AppDto,
) {
    Row(
        modifier = modifier.fillMaxWidth(.9f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter =
                if (appDto.icon != null) rememberAsyncImagePainter(model = appDto.icon)
                else rememberAsyncImagePainter(model = R.drawable.default_icon),
            "${appDto.name} Icon",
            contentScale = ContentScale.Crop,
            modifier = modifier.padding(horizontal = 16.dp).size(48.dp, 48.dp).clip(CircleShape)
        )

        Box(
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(RoundedCornerShape(16.dp, 16.dp, 16.dp, 16.dp))
                    .background(Color(0xFFE6E5EB))
        ) {
            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text =
                        "You used me for ${appDto.screenTime.milliseconds.toComponents { hh, mm, _, _ ->
                    "${hh}h ${mm}min"
                } }",
                    style =
                        TextStyle(
                            fontSize = 18.sp,
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
                            color = NeobrutalismTheme.colors.text
                        )
                )
            }
        }
    }
}
