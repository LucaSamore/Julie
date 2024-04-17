package com.example.julie.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.julie.R
import com.example.julie.ui.theme.textColor

@Composable
fun NeubrutalErrorMessage(message: String) {
    Text(
        text = message,
        style =
            TextStyle(
                color = textColor,
                fontFamily = FontFamily(Font(R.font.inconsolata_variable)),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
    )
}
