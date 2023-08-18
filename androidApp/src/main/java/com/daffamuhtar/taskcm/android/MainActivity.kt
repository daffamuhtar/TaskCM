package com.daffamuhtar.taskcm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.daffamuhtar.taskcm.AppBird

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            AppBird()
//            App(
//                darkTheme = isSystemInDarkTheme(),
//                dynamicColor = true,
//                appModule = AppModule(LocalContext.current.applicationContext),
//                imagePicker = ImagePickerFactory().createPicker()
//
//            )
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
