package com.maxidev.moviehub.common.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maxidev.moviehub.feature.settings.data.utils.TypeTheme

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFff8c94),
    secondary = Color(0xFFffc3a0),
    tertiary = Color(0xFFd5aaFF),
    onPrimary = Color(0xFF000000),
    primaryContainer = Color(0xFF592541),
    onPrimaryContainer = Color(0xFFFFDCE5),
    inversePrimary = Color(0xFFffccd5),
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFF50322D),
    onSecondaryContainer = Color(0xFFFFE3D5),
    onTertiary = Color(0xFF000000),
    tertiaryContainer = Color(0xFF3C2D52),
    onTertiaryContainer = Color(0xFFE8D6FF),
    background = Color(0xFF1B1F30),
    onBackground = Color(0xFFF0E8FF),
    surface = Color(0xFF1B1F30),
    onSurface = Color(0xFFF0E8FF),
    surfaceVariant = Color(0xFF322845),
    onSurfaceVariant = Color(0xFFD3CCE3),
    surfaceTint = Color(0xFFff8c94),
    inverseSurface = Color(0xFFF0E8FF),
    inverseOnSurface = Color(0xFF1B1F30),
    error = Color(0xFFFF6F61),
    onError = Color(0xFF000000),
    errorContainer = Color(0xFF5A1E23),
    onErrorContainer = Color(0xFFFFD7D1),
    outline = Color(0xFFD3CCE3),
    outlineVariant = Color(0xFF292D3E),
    scrim = Color(0xFF000000),
    surfaceBright = Color(0xFF22263C),
    surfaceContainer = Color(0xFF2C304A),
    surfaceContainerHigh = Color(0xFF363B54),
    surfaceContainerHighest = Color(0xFF40475F),
    surfaceContainerLow = Color(0xFF171B2C),
    surfaceContainerLowest = Color(0xFF121628),
    surfaceDim = Color(0xFF0E1220)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFF8C94),
    secondary = Color(0xFFFFC3A0),
    tertiary = Color(0xFFD5AAFF),
    onPrimary = Color(0xFF000000),
    primaryContainer = Color(0xFFFFDDE0),
    onPrimaryContainer = Color(0xFF6E1C28),
    inversePrimary = Color(0xFFFFCCD5),
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFFFFE3D5),
    onSecondaryContainer = Color(0xFF6B3C2A),
    onTertiary = Color(0xFF000000),
    tertiaryContainer = Color(0xFFE8D6FF),
    onTertiaryContainer = Color(0xFF3C2D52),
    background = Color(0xFFFDF6FF),
    onBackground = Color(0xFF1C1C1C),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1C1C1C),
    surfaceVariant = Color(0xFFF7F1FF),
    onSurfaceVariant = Color(0xFF4D4D4D),
    surfaceTint = Color(0xFFFF8C94),
    inverseSurface = Color(0xFF1C1C1C),
    inverseOnSurface = Color(0xFFFDF6FF),
    error = Color(0xFFFF6F61),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFD7D1),
    onErrorContainer = Color(0xFF5A1E23),
    outline = Color(0xFFD3CCE3),
    outlineVariant = Color(0xFFF0E8FF),
    scrim = Color(0xFF000000),
    surfaceBright = Color(0xFFFDF6FF),
    surfaceContainer = Color(0xFFFFFAFF),
    surfaceContainerHigh = Color(0xFFF7F1FF),
    surfaceContainerHighest = Color(0xFFF2ECFF),
    surfaceContainerLow = Color(0xFFFFFFFF),
    surfaceContainerLowest = Color(0xFFF7F1FF),
    surfaceDim = Color(0xFFF0E8FF)
)

@Composable
fun MovieHubTheme(
    viewModel: ThemeViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val isDynamic by viewModel.isDynamic.collectAsStateWithLifecycle()
    val isTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()
    val isDarkTheme = when (isTheme) {
        TypeTheme.SYSTEM -> isSystemInDarkTheme()
        TypeTheme.DARK -> true
        TypeTheme.LIGHT -> false
    }
    val colorScheme = when {
        isDynamic && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.navigationBarColor = colorScheme.surfaceContainer.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !isDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}