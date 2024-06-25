package com.example.julie.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.julie.R
import com.example.julie.ui.theme.NeobrutalismTheme

@Composable
internal fun CaptionDescription(modifier: Modifier, text: String) {
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
