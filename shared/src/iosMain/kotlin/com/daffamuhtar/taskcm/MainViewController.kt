package com.daffamuhtar.taskcm

import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import com.daffamuhtar.taskcm.core.presentation.ImagePicker
import com.daffamuhtar.taskcm.core.presentation.ImagePickerFactory
import com.daffamuhtar.taskcm.di.AppModule
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController () = ComposeUIViewController{
    val isDarkTheme =
        UIScreen.mainScreen().traitCollection.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark
    App (
        darkTheme = isDarkTheme,
        dynamicColor = true,
        appModule = AppModule(),
        imagePicker = ImagePickerFactory(LocalUIViewController.current).createPicker()
    )
}