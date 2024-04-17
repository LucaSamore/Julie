package com.example.julie.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation

@Composable
fun NeubrutalPrimaryButton(
    modifier: Modifier,
    text: String,
    width: Float = 0.9f,
    height: Dp = 48.dp,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(width).height(height).neubrutalismElevation(),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = NeobrutalismTheme.colors.buttonPrimary,
                contentColor = NeobrutalismTheme.colors.text,
                disabledContainerColor = NeobrutalismTheme.colors.buttonPrimary,
                disabledContentColor = NeobrutalismTheme.colors.buttonPrimary,
            ),
        shape = NeobrutalismTheme.shapes.surface,
        onClick = onClick
    ) {
        Text(
            text = text,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = NeobrutalismTheme.typography.buttons
        )
    }
}
