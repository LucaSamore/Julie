package com.example.julie.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.julie.R
import com.example.julie.ui.theme.NeobrutalismTheme

@Composable
fun NeubrutalLabel(modifier: Modifier, text: String, textAlign: TextAlign = TextAlign.Right) {
    Box(
        modifier = modifier,
    ) {
        Text(
            text = text,
            textAlign = textAlign,
            fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
            color = NeobrutalismTheme.colors.background,
            fontSize = 24.sp
        )

        Text(text = text, textAlign = textAlign, style = NeobrutalismTheme.typography.lables)
    }
}
