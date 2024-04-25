package com.example.julie.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.julie.R

@Immutable
data class NeobrutalismColors(
    val background: Color,
    val contentPrimary: Color,
    val contentSecondary: Color,
    val contentTertiary: Color,
    val text: Color,
    val buttonPrimary: Color,
    val buttonSecondary: Color
)

@Immutable
data class NeobrutalismTypography(
    val mainTitle: TextStyle,
    val screenTitle: TextStyle,
    val lables: TextStyle,
    val buttons: TextStyle,
    val text: TextStyle
)

@Immutable data class NeobrutalismShapes(val component: Shape, val surface: Shape)

val LocalNeobrutalismColors = staticCompositionLocalOf {
    NeobrutalismColors(
        background = Color.Unspecified,
        contentPrimary = Color.Unspecified,
        contentSecondary = Color.Unspecified,
        contentTertiary = Color.Unspecified,
        text = Color.Unspecified,
        buttonPrimary = Color.Unspecified,
        buttonSecondary = Color.Unspecified,
    )
}

val LocalNeobrutalismTypography = staticCompositionLocalOf {
    NeobrutalismTypography(
        mainTitle = TextStyle.Default,
        screenTitle = TextStyle.Default,
        lables = TextStyle.Default,
        buttons = TextStyle.Default,
        text = TextStyle.Default
    )
}

val LocalNeobrutalismShapes = staticCompositionLocalOf {
    NeobrutalismShapes(
        component = RoundedCornerShape(ZeroCornerSize),
        surface = RoundedCornerShape(ZeroCornerSize)
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun NeobrutalismTheme(content: @Composable () -> Unit) {
    val neobrutalismColors =
        NeobrutalismColors(
            background = backgroundColor,
            contentPrimary = Color(0xFF7F72FF),
            contentSecondary = Color(0xFFB4ADF9),
            contentTertiary = Color(0xFF7BE6CD),
            text = textColor,
            buttonPrimary = Color(0xFF7BE6CD),
            buttonSecondary = Color(0xFFFFD787)
        )

    val neobrutalismTypography =
        NeobrutalismTypography(
            mainTitle =
                TextStyle(
                    fontSize = 48.sp,
                    fontFamily =
                        FontFamily(
                            Font(
                                R.font.nunito_variable,
                                variationSettings =
                                    FontVariation.Settings(
                                        FontVariation.weight(800),
                                    )
                            )
                        ),
                    color = textColor
                ),
            screenTitle =
                TextStyle(
                    fontSize = 24.sp,
                    fontFamily =
                        FontFamily(
                            Font(
                                R.font.inconsolata_variable,
                                variationSettings =
                                    FontVariation.Settings(
                                        FontVariation.width(140f),
                                        FontVariation.weight(500)
                                    )
                            )
                        ),
                    color = textColor,
                    fontWeight = FontWeight.Bold
                ),
            lables =
                TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
                    color = textColor,
                    drawStyle = Stroke(width = 3f, join = StrokeJoin.Round)
                ),
            buttons =
                TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_mega_variable)),
                    fontWeight = FontWeight.W900
                ),
            text =
                TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inconsolata_variable)),
                    fontWeight = FontWeight.Bold
                )
        )

    val neobrutalismShape =
        NeobrutalismShapes(
            component = RoundedCornerShape(percent = 10),
            surface = RoundedCornerShape(size = 4.dp)
        )

    CompositionLocalProvider(
        LocalNeobrutalismColors provides neobrutalismColors,
        LocalNeobrutalismTypography provides neobrutalismTypography,
        LocalNeobrutalismShapes provides neobrutalismShape,
        content = content
    )
}

object NeobrutalismTheme {
    val colors: NeobrutalismColors
        @Composable get() = LocalNeobrutalismColors.current

    val typography: NeobrutalismTypography
        @Composable get() = LocalNeobrutalismTypography.current

    val shapes: NeobrutalismShapes
        @Composable get() = LocalNeobrutalismShapes.current
}
