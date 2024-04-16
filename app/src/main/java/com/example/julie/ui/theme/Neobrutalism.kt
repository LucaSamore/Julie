package com.example.julie.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    val buttons: TextStyle
)

@Immutable
data class NeobrutalismShapes(
    val component: Shape,
    val surface: Shape
)

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
    )
}

val LocalNeobrutalismShapes = staticCompositionLocalOf {
    NeobrutalismShapes(
        component = RoundedCornerShape(ZeroCornerSize),
        surface = RoundedCornerShape(ZeroCornerSize)
    )
}

@Composable
fun NeobrutalismTheme(content: @Composable () -> Unit) {
    val neobrutalismColors = NeobrutalismColors(
        background = Color(0xFFFFFAF0),
        contentPrimary = Color(0xFF7F72FF),
        contentSecondary = Color(0xFFB4ADF9),
        contentTertiary = Color(0xFF7BE6CD),
        text = Color(0xFF1E1E1E),
        buttonPrimary = Color(0xFF7BE6CD),
        buttonSecondary = Color(0xFFFFD787)
    )

    val neobrutalismTypography = NeobrutalismTypography(
        mainTitle = TextStyle(fontSize = 48.sp),
        screenTitle = TextStyle(fontSize = 24.sp),
        lables = TextStyle(fontSize = 16.sp),
        buttons = TextStyle(fontSize = 16.sp)
    )

    val neobrutalismShape = NeobrutalismShapes(
        component = RoundedCornerShape(percent = 10),
        surface = RoundedCornerShape(size = 4.dp)
    )

    CompositionLocalProvider(
        LocalNeobrutalismColors provides neobrutalismColors,
        LocalNeobrutalismTypography provides  neobrutalismTypography,
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