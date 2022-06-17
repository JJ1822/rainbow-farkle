package com.example.rainbowfarkle.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Purple500,
    primaryVariant = Purple200,
    secondary = Green,
    background = White,
    surface = White,
    onPrimary = Pink100,
    onSecondary = Pink100,
    onBackground = Black,
    onSurface = Black,
    error = Red
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple200,
    secondary = Green,
    background = White,
    surface = White,
    onPrimary = Pink100,
    onSecondary = Pink100,
    onBackground = Black,
    onSurface = Black,
    error = Red
)

@Composable
fun RainbowFarkleTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}