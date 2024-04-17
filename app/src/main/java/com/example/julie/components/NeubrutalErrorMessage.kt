package com.example.julie.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.julie.R
import com.example.julie.ui.theme.backgroundColor

@Composable
fun NeubrutalErrorMessage(
    modifier: Modifier,
    message: String,
    width: Float = 0.8f,
    color: Color = backgroundColor
) {
    Text(
        modifier = modifier.fillMaxWidth(width),
        text = message,
        style =
            TextStyle(
                color = color,
                fontFamily = FontFamily(Font(R.font.inconsolata_variable)),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
    )
}
