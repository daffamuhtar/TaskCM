package com.daffamuhtar.taskcm.core.presentation

import androidx.compose.runtime.Composable

expect fun ContactsTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
)