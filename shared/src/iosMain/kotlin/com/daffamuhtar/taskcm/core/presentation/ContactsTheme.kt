package com.daffamuhtar.taskcm.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.daffamuhtar.taskcm.ui.theme.DarkColorScheme
import com.daffamuhtar.taskcm.ui.theme.LightColorScheme
import com.daffamuhtar.taskcm.ui.theme.Typography

@Composable
actual fun ContactsTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
            typography = Typography,
            content = content
        )
    }
}