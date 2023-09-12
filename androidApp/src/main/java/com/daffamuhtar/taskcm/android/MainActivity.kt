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
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.daffamuhtar.taskcm.App
import com.daffamuhtar.taskcm.approval.ApprovalViewModel
import com.daffamuhtar.taskcm.approval.MainViewModel
import com.daffamuhtar.taskcm.approval.data.response.LoginResponse
import com.daffamuhtar.taskcm.approval.utils.LoginState
import com.daffamuhtar.taskcm.core.presentation.ImagePickerFactory
import com.daffamuhtar.taskcm.di.AppModule
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.gson.Gson
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.math.log

class MainActivity : ComponentActivity() {

    val sharedPreferencesId = "taskCM_sharedPref"

    val EXTRA_USER_ID = "EXTRA_USER_ID"
    val EXTRA_POSITION = "EXTRA_POSITION"
    val EXTRA_APPROVAL_LEVEL = "EXTRA_APPROVAL_LEVEL"
    val EXTRA_TOKEN = "EXTRA_TOKEN"
    val EXTRA_COMPANY_TYPE = "EXTRA_COMPANY_TYPE"
    val EXTRA_COMPANY_NAME = "EXTRA_COMPANY_NAME"

    @SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
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

            val mainViewModel =  getViewModel(
                key ="mainViewModel",
                factory = viewModelFactory {
                    MainViewModel()
                }
            )

            val stateMain by mainViewModel.state.collectAsState()


            if (stateMain.isSuccessGetSession){
                stateMain.loginResponse.let {
                    saveSession(it)
                }
            }else{
                getLoginSession()?.let { mainViewModel.saveLoginReponse(it) }
            }



            App(
                darkTheme = false,
                dynamicColor = false,
                appModule = AppModule(LocalContext.current.applicationContext),
                imagePicker = ImagePickerFactory().createPicker(),
                fcmToken = token,
                loggedUserId = "userId",
                userToken = "userToken",
                mainViewModel = mainViewModel,
                stateMain = stateMain,

            )
//            }

        }
    }

    public fun hehe() {

    }

    public fun saveSession(loginResponse: LoginResponse?) {
        if (loginResponse != null) {
            val editor = getSharedPreferences(sharedPreferencesId, MODE_PRIVATE).edit()
            val gson = Gson()

            val json = gson.toJson(loginResponse)

            Log.d("saveSession json", "" + json)

            editor.putString("loginResponseObject", json)
            editor.commit()

            Toast.makeText(applicationContext, "Tersimpan", Toast.LENGTH_SHORT).show()

        } else {
            Log.d("saveSession json", "" + "gagal tersimpaaan")
            Toast.makeText(applicationContext, "Gagal tersimpan", Toast.LENGTH_SHORT).show()

        }
    }

    fun getLoginSession(): LoginResponse? {

        val sharedpreferences = getSharedPreferences(sharedPreferencesId, MODE_PRIVATE)

        val gson = Gson()
        val json: String? = sharedpreferences.getString("loginResponseObject", null)
        json?.let {
            Log.d("getSession json", it)
        } ?: run {
            Log.d("getSession json", "ga dapet")
        }

        var obj: LoginResponse? = null
        if (json != null) {
            obj = gson.fromJson(json, LoginResponse::class.java)
        }

        return obj


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


//
//_state.value.loginResponse?.companyName?.let {
//    Log.d("saveSession", it)
//
//} ?: run {
//    Log.d("saveSession", "hueeeee gada")
//
//}
//            state.value.loginResponse?.let {
//
//                saveSession(
//                    it
//                )
//                Log.d("saveSession", "Tersimpan - " + it)
//
//            } ?: run {
//
//                Log.d("saveSession", "Kosong - get saved")
//
//
//                _state.update {
//                    it.copy(
//                        loginResponse = getLoginSession()
//                    )
//                }
//
//            }


//            if (state.value.isSuccessGetSession) {