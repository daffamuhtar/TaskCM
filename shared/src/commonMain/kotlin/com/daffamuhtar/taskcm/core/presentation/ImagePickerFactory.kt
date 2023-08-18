package com.daffamuhtar.taskcm.core.presentation

import androidx.compose.runtime.Composable
import com.daffamuhtar.taskcm.core.presentation.ImagePicker

expect class ImagePickerFactory {

    @Composable
    fun createPicker(): ImagePicker
}