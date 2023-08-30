package com.daffamuhtar.taskcm.android

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.daffamuhtar.taskcm.App
import com.daffamuhtar.taskcm.core.presentation.ImagePickerFactory
import com.daffamuhtar.taskcm.di.AppModule
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current
            val gckTokenKey = stringPreferencesKey("gmc_token")

            val fcmToken = flow<String> {
                context.dataStore.data.map {
                    it[gckTokenKey]
                }.collect(collector = {
                    if (it != null) {
                        this.emit(it)
                    }
                })
            }.collectAsState(initial = "init")

            var token: String? = null
//         Toast.makeText(this, ""+fcmToken, Toast.LENGTH_SHORT).show()
            lifecycleScope.launch {
                token = Firebase.messaging.token.await()
                Log.d("TOKENN", "onCreate: " + token)
            }


//            AppBird()
            App(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = true,
                appModule = AppModule(LocalContext.current.applicationContext),
                imagePicker = ImagePickerFactory().createPicker(),
                fcmToken = token
            )
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
