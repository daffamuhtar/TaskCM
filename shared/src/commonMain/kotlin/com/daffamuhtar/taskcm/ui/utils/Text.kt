package com.daffamuhtar.taskcm.ui.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SubtitleText(subtitle: String, modifier: Modifier = Modifier) {
    Text(text = subtitle, style = typography.headlineMedium, modifier = modifier.padding(8.dp))
}

@Composable
fun TitleText(title: String) {
    Text(
        text = title,
        style = typography.headlineLarge.copy(fontSize = 14.sp),
        modifier = Modifier.padding(8.dp)
    )
}