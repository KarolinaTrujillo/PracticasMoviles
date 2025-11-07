package com.karo.examu2.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.karo.examu2.data.datastore.ThemeDataStore
import com.karo.examu2.viewmodel.ThemeViewModel

private val DarkColors = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF1565C0),
    onPrimary = Color.White,
    background = Color(0xFFFFFFFF),
    onBackground = Color.Black,
    surface = Color(0xFFF2F2F2),
    onSurface = Color.Black
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val dataStore = ThemeDataStore(context)
    val viewModel = ThemeViewModel(dataStore)
    val isDark = viewModel.darkMode.collectAsState(initial = isSystemInDarkTheme())

    val colors = if (isDark.value) DarkColors else LightColors

    MaterialTheme(colorScheme = colors, typography = MaterialTheme.typography) {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background,
            content = content
        )
    }
}
