package com.iscoding.designsystem.theme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// for text
val LightGrayText = Color(0xFFE3E1E9)
val DarkGrayText = Color(0xFFB8C3FF)
// dark
val AppBlueDark= Color(0xFF0040DF)
val AppDarkBlueDark = Color(0xFF0A369D)
val AppLightBlueDark = Color(0xFF0040DF)


// Brand blue — same across both themes
val WalletBlue = Color(0xFF2D5BFF)
val WalletBlueDark = Color(0xFF1A3FCC)
val WalletBlueLight = Color(0xFFDCE6FF)
val WalletLightBlue = Color(0xFF2D5BFF)

// Semantic — also constant across both themes
val WalletGreen = Color(0xFF1FAA59)
val WalletRed = Color(0xFFE5484D)
val WalletAmber = Color(0xFFF5A524)

// Universal
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)

// Neutrals — Light theme
val LightBackground = Color(0xFFFAFAFC)
val LightSurface = Color(0xFFFFFFFF)
val LightSurfaceVariant = Color(0xFFEAEDFF)
val LightOnBackground = Color(0xFF1A1B23)
val LightOnSurface = Color(0xFF1A1B23)
val LightOnSurfaceVariant = Color(0xFF5B5D6B)
val LightOutline = Color(0xFFE1E2E8)

// Neutrals — Dark theme
val DarkBackground = Color(0xFF101116)
val DarkSurface = Color(0xFF1A1B23)
val DarkSurfaceVariant = Color(0xFF23242D)
val DarkOnBackground = Color(0xFFF2F2F5)
val DarkOnSurface = Color(0xFFF2F2F5)
val DarkOnSurfaceVariant = Color(0xFFA9ABB8)
val DarkOutline = Color(0xFF33343D)
data class WalletAppColors(
    val primary: Color,
    val primaryDark: Color,
    val primaryContainer: Color,

    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,

    val onPrimary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,

    val outline: Color,

    // Semantic colors
    val success: Color,
    val error: Color,
    val warning: Color,
)


val LightColors = WalletAppColors(
    // Brand
    primary = WalletBlue,
    primaryDark = WalletBlueDark,
    primaryContainer = WalletLightBlue,

    // Surfaces
    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = LightSurfaceVariant,

    // Content
    onPrimary = White,
    onBackground = LightOnBackground,
    onSurface = LightOnSurface,
    onSurfaceVariant = LightOnSurfaceVariant,

    // Borders
    outline = LightOutline,

    // Semantic
    success = WalletGreen,
    error = WalletRed,
    warning = WalletAmber,
)
val DarkColors = WalletAppColors(
    // Brand
    primary = WalletBlueDark,
    primaryDark = AppBlueDark,
    primaryContainer = AppDarkBlueDark,

    // Surfaces
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,

    // Content
    onPrimary = White,
    onBackground = DarkOnBackground,
    onSurface = DarkOnSurface,
    onSurfaceVariant = DarkOnSurfaceVariant,

    // Borders
    outline = DarkOutline,

    // Semantic
    success = WalletGreen,
    error = WalletRed,
    warning = WalletAmber,
)
internal val LocalWalletAppColors =
    staticCompositionLocalOf { LightColors }
