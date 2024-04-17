package com.example.julie.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.julie.R
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.backgroundColor
import com.example.julie.ui.theme.textColor

@Composable
fun NeubrutalTextField(
    modifier: Modifier,
    value: String,
    placeholder: String,
    onValueChanged: (newValue: String) -> Unit
) {
    TextField(
        value = value,
        singleLine = true,
        modifier =
            modifier
                .fillMaxWidth(.8f)
                .border(width = 2.dp, color = textColor, shape = NeobrutalismTheme.shapes.surface),
        onValueChange = onValueChanged,
        placeholder = {
            Text(
                modifier = modifier.alpha(0.75f),
                text = placeholder,
                style =
                    TextStyle(
                        fontFamily = FontFamily(Font(R.font.nunito_variable)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
            )
        },
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = backgroundColor,
                focusedTextColor = textColor,
                disabledTextColor = Color.Transparent,
                unfocusedContainerColor = backgroundColor,
                unfocusedTextColor = textColor,
                disabledContainerColor = backgroundColor,
                focusedPlaceholderColor = textColor,
                unfocusedPlaceholderColor = textColor,
                disabledPlaceholderColor = textColor,
                cursorColor = textColor,
                errorCursorColor = textColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
        textStyle =
            TextStyle(
                fontFamily = FontFamily(Font(R.font.nunito_variable)),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
            ),
        shape = NeobrutalismTheme.shapes.surface
    )
}
