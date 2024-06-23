package com.example.julie.components.neubrutalism

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.julie.ui.theme.NeobrutalismTheme

@Composable
internal fun NeubrutalLabel(modifier: Modifier, text: String) {
    Text(
        modifier = modifier.fillMaxWidth(.8f),
        text = text,
        style = NeobrutalismTheme.typography.lables,
    )
}
