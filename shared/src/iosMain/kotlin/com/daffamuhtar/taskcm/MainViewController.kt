package com.daffamuhtar.taskcm

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import com.daffamuhtar.taskcm.approval.MainViewModel
import com.daffamuhtar.taskcm.core.presentation.ImagePickerFactory
import com.daffamuhtar.taskcm.di.AppModule
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import platform.Foundation.NSUserDefaults
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController() = ComposeUIViewController {
    val isDarkTheme =
        UIScreen.mainScreen().traitCollection.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark
//    AppBird()
    NSUserDefaults.standardUserDefaults.setInteger(50, "Key")
    var page = NSUserDefaults.standardUserDefaults.integerForKey("Key").toInt()


    val mainViewModel = getViewModel(
        key = "mainViewModel",
        factory = viewModelFactory {
            MainViewModel()
        }
    )

    val stateMain by mainViewModel.state.collectAsState()



    if (stateMain.isSuccessGetSession) {
        stateMain.loginResponseString?.let {
            saveSession(it, mainViewModel)
        }

    } else {

        print("here yaaaa")
        getLoginSession()?.let { mainViewModel.saveLoginReponse(null,it) }
    }

    App(
        darkTheme = isDarkTheme,
        dynamicColor = false,
        appModule = AppModule(),
        imagePicker = ImagePickerFactory(LocalUIViewController.current).createPicker(),
        fcmToken = null,
        loggedUserId = "userId",
        userToken = "userToken",
        mainViewModel = mainViewModel,
        stateMain = stateMain,
        count = page
    )


}

public fun saveSession(loginResponse: String, viewModel: MainViewModel) {
    NSUserDefaults.standardUserDefaults.setObject(loginResponse, "loginResponse")
}

public fun getLoginSession(): String? {
    var loginResponse : String? = null

    NSUserDefaults.standardUserDefaults.objectForKey("loginResponse")?.let {
        loginResponse= it.toString()

    }

    print("IOS SESSION" + NSUserDefaults.standardUserDefaults.objectForKey("loginResponse"))


    return loginResponse

}


