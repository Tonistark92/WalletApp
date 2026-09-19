    package com.iscoding.designsystem.theme

    import androidx.compose.foundation.isSystemInDarkTheme
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.darkColorScheme
    import androidx.compose.material3.lightColorScheme
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.CompositionLocalProvider
    import androidx.compose.runtime.ReadOnlyComposable
    import androidx.compose.runtime.staticCompositionLocalOf


    @Composable
    fun WalletAppTheme(
        useDarkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
    ) {
        val colors = if (useDarkTheme) {
            DarkColors
        } else {
            LightColors
        }

        val materialColors = if (useDarkTheme) {
            darkColorScheme(
                primary = colors.primary,
                onPrimary = colors.onPrimary,
                primaryContainer = colors.primaryContainer,

                background = colors.background,
                onBackground = colors.onBackground,

                surface = colors.surface,
                onSurface = colors.onSurface,

                surfaceVariant = colors.surfaceVariant,
                onSurfaceVariant = colors.onSurfaceVariant,

                outline = colors.outline,

                error = colors.error,
                onError = White
            )
        } else {
            lightColorScheme(
                primary = colors.primary,
                onPrimary = colors.onPrimary,
                primaryContainer = colors.primaryContainer,

                background = colors.background,
                onBackground = colors.onBackground,

                surface = colors.surface,
                onSurface = colors.onSurface,

                surfaceVariant = colors.surfaceVariant,
                onSurfaceVariant = colors.onSurfaceVariant,

                outline = colors.outline,

                error = colors.error,
                onError = White
            )
        }

        CompositionLocalProvider(
            LocalWalletAppColors provides colors,
            LocalWalletAppTypography provides WalletTypography,
            LocalWalletAppShapes provides WalletShapes,
            LocalWalletAppSpacing provides WalletSpacing,
            LocalWalletAppIcons provides WalletAppIcons,
        ) {
            MaterialTheme(
                colorScheme = materialColors,
                content = content
            )
        }
    }


    object WalletAppTheme {

        val colors: WalletAppColors
            @Composable
            @ReadOnlyComposable
            get() = LocalWalletAppColors.current

        val typography: WalletAppTypography
            @Composable
            @ReadOnlyComposable
            get() = LocalWalletAppTypography.current

        val shapes: WalletAppShapes
            @Composable
            @ReadOnlyComposable
            get() = LocalWalletAppShapes.current

        val spacing: WalletAppSpacing
            @Composable
            @ReadOnlyComposable
            get() = LocalWalletAppSpacing.current

        val icons: WalletAppIcons
            @Composable
            @ReadOnlyComposable
            get() = LocalWalletAppIcons.current
    }