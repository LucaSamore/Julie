package com.example.julie.components.neubrutalism

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.julie.R
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.textColor

@Composable
internal fun NeubrutalPointsStreakBox(
    modifier: Modifier,
    points: Int,
    streak: Int,
    verticalPadding: Dp = 32.dp
) {
    Row(modifier = modifier.fillMaxWidth(.9f).padding(vertical = verticalPadding)) {
        Box(
            modifier =
                modifier
                    .fillMaxWidth(.5f)
                    .height(78.dp)
                    .border(2.dp, NeobrutalismTheme.colors.text, RoundedCornerShape(4.dp))
                    .background(color = NeobrutalismTheme.colors.buttonPrimary)
        ) {
            Column(
                modifier = modifier.fillMaxSize().padding(vertical = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = points.toString(),
                    style =
                        TextStyle(
                            fontSize = 28.sp,
                            fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
                            color = textColor,
                        )
                )

                Spacer(modifier = modifier.padding(vertical = 2.dp))

                Text(
                    text = "Points",
                    style =
                        TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
                            color = textColor,
                        )
                )
            }
        }

        Spacer(modifier = modifier.padding(horizontal = 8.dp))

        Box(
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(78.dp)
                    .border(2.dp, NeobrutalismTheme.colors.text, RoundedCornerShape(4.dp))
                    .background(color = NeobrutalismTheme.colors.buttonPrimary)
        ) {
            Column(
                modifier = modifier.fillMaxSize().padding(vertical = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = streak.toString(),
                    style =
                        TextStyle(
                            fontSize = 28.sp,
                            fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
                            color = textColor,
                        )
                )

                Spacer(modifier = modifier.padding(vertical = 2.dp))

                Text(
                    text = "Current Streak",
                    style =
                        TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
                            color = textColor,
                        )
                )
            }
        }
    }
}
