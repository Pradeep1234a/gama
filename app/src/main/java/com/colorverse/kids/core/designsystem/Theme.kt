package com.colorverse.kids.core.designsystem

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = ElectricCoral,
    onPrimary = KidSurfaceLight,
    primaryContainer = ElectricCoralLight,
    onPrimaryContainer = KidBackgroundDark,
    secondary = SunshineGold,
    onSecondary = KidBackgroundDark,
    secondaryContainer = SunshineGoldLight,
    tertiary = MintMeadow,
    onTertiary = KidBackgroundDark,
    tertiaryContainer = MintMeadowLight,
    background = KidBackgroundLight,
    surface = KidSurfaceLight,
    surfaceVariant = KidSurfaceVariantLight
)

private val DarkColorScheme = darkColorScheme(
    primary = ElectricCoralDark,
    onPrimary = KidBackgroundDark,
    primaryContainer = ElectricCoral,
    secondary = SunshineGoldDark,
    onSecondary = KidBackgroundDark,
    secondaryContainer = SunshineGold,
    tertiary = MintMeadowDark,
    onTertiary = KidBackgroundDark,
    tertiaryContainer = MintMeadow,
    background = KidBackgroundDark,
    surface = KidSurfaceDark,
    surfaceVariant = KidSurfaceVariantDark
)

@Composable
fun ColorVerseKidsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = KidsTypography,
        shapes = KidsShapes,
        content = content
    )
}
