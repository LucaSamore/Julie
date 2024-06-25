package com.example.julie.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.report.ReportDto
import com.example.julie.R
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation
import com.example.julie.ui.theme.textColor

@Composable
internal fun AppUsageSwipeableCard(modifier: Modifier, reportDto: ReportDto) {
    Box(
        modifier =
            modifier
                .neubrutalismElevation(cornersRadius = 0.dp, borderWidth = 4.dp)
                .fillMaxWidth(.9f)
                .fillMaxHeight()
                .background(Color.White)
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier =
                    modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .background(NeobrutalismTheme.colors.buttonSecondary)
                        .drawBehind {
                            val strokeWidth = 6f
                            val y = size.height - strokeWidth / 2
                            drawLine(textColor, Offset(0f, y), Offset(size.width, y), strokeWidth)
                        },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "App Usage",
                    style =
                        TextStyle(
                            fontSize = 36.sp,
                            fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
                            color = textColor,
                        ),
                    modifier = modifier.fillMaxWidth(.5f).padding(horizontal = 16.dp)
                )

                Text(
                    text = reportDto.date.toString(),
                    style =
                        TextStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.inconsolata_variable)),
                            color = textColor.copy(alpha = .5f),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                    modifier = modifier.fillMaxWidth()
                )
            }

            LazyColumn(
                modifier = modifier.padding(vertical = 16.dp),
                verticalArrangement = Arrangement.Top,
            ) {
                items(reportDto.appReports.sortedByDescending { it.screenTime }) {
                    AppMessage(modifier = modifier, appDto = it)
                    AppReactions(modifier = modifier, appDto = it)
                }
            }
        }
    }
}
