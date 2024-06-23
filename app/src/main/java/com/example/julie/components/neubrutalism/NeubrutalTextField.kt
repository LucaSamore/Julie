package com.example.julie.components.neubrutalism

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.julie.bounceClick
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation
import com.example.julie.ui.theme.textColor

@Composable
internal fun NeubrutalTextField(
    modifier: Modifier,
    value: String,
    placeholder: String,
    label: String,
    errorMessage: String,
    errorMessageHidden: Boolean,
    onValueChanged: (newValue: String) -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        NeubrutalLabel(modifier = modifier, text = label)

        if (!errorMessageHidden) {
            NeubrutalErrorMessage(modifier = modifier, message = errorMessage)
        }

        TextField(
            value = value,
            singleLine = true,
            modifier = modifier.fillMaxWidth(.8f).bounceClick().neubrutalismElevation(),
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
                    focusedContainerColor = Color.White,
                    focusedTextColor = textColor,
                    disabledTextColor = Color.Transparent,
                    unfocusedContainerColor = Color.White,
                    unfocusedTextColor = textColor,
                    disabledContainerColor = Color.White,
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
}
