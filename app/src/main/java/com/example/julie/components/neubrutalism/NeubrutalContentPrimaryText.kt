package com.example.julie.components.neubrutalism

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.example.julie.R
import com.example.julie.ui.theme.NeobrutalismTheme

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun NeubrutalContentPrimaryText(
    modifier: Modifier,
    content: String,
    fontSize: TextUnit,
    fontWeight: Int = 500,
    textAlign: TextAlign,
) {
    Text(
        text = content,
        modifier = modifier.fillMaxWidth(),
        style =
            TextStyle(
                fontSize = fontSize,
                textAlign = textAlign,
                fontFamily =
                    FontFamily(
                        Font(
                            R.font.nunito_variable,
                            variationSettings =
                                FontVariation.Settings(
                                    FontVariation.weight(fontWeight),
                                )
                        )
                    ),
                fontWeight = FontWeight.W900,
                color = NeobrutalismTheme.colors.background,
            )
    )
}
