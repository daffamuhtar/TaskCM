package com.daffamuhtar.taskcm.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.daffamuhtar.taskcm.theme.DarkColorScheme
import com.daffamuhtar.taskcm.theme.LightColorScheme
import com.daffamuhtar.taskcm.theme.Typography

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