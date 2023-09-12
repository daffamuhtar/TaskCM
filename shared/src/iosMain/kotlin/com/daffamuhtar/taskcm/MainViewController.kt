package com.daffamuhtar.taskcm

import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import com.daffamuhtar.taskcm.core.presentation.ImagePickerFactory
import com.daffamuhtar.taskcm.di.AppModule
import com.daffamuhtar.taskcm.theme.color_yellow
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController () = ComposeUIViewController{
    val isDarkTheme =
        UIScreen.mainScreen().traitCollection.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark
//    AppBird()

    App (
        darkTheme = isDarkTheme,
        dynamicColor = true,
        appModule = AppModule(),
        imagePicker = ImagePickerFactory(LocalUIViewController.current).createPicker(),
        fcmToken = null,
        loggedUserId = "userId",
        userToken = "userToken",
        mainViewModel = null,
        stateMain = null,
    )
}