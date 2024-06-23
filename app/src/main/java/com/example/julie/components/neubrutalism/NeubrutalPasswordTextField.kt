package com.example.julie.components.neubrutalism

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.julie.R
import com.example.julie.ui.theme.NeobrutalismTheme
import com.example.julie.ui.theme.neubrutalismElevation
import com.example.julie.ui.theme.textColor

@Composable
internal fun NeubrutalPasswordTextField(
    modifier: Modifier,
    password: String,
    errorMessage: String,
    errorMessageHidden: Boolean,
    onPasswordChange: (password: String) -> Unit
) {
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = modifier.padding(vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        NeubrutalLabel(modifier = modifier, text = "Password")

        if (!errorMessageHidden) {
            NeubrutalErrorMessage(modifier = modifier, message = errorMessage)
        }

        TextField(
            value = password,
            singleLine = true,
            modifier = modifier.fillMaxWidth(.8f).neubrutalismElevation(),
            onValueChange = onPasswordChange,
            placeholder = {
                Text(
                    modifier = modifier.alpha(0.75f),
                    text = "••••••••••",
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
            shape = NeobrutalismTheme.shapes.surface,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation =
                if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (!passwordHidden) R.drawable.baseline_visibility_24
                        else R.drawable.baseline_visibility_off_24
                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(
                        imageVector = ImageVector.vectorResource(visibilityIcon),
                        contentDescription = description
                    )
                }
            }
        )
    }
}
