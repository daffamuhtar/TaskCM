package com.daffamuhtar.taskcm.approval

import com.daffamuhtar.taskcm.approval.data.response.LoginResponse
import com.daffamuhtar.taskcm.approval.utils.LoginState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainViewModel(
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())

    val state = _state.asStateFlow()


    fun saveLoginReponse(loginResponse: LoginResponse?, loginResponseString: String?) {


        if (loginResponseString == null) {
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        loginResponse = loginResponse,
                        isSuccessGetSession = true,
                        loginResponseString = loginResponseString
                    )
                }
            }
        } else {

            val newLoginResponse: LoginResponse = Json.decodeFromString(
                LoginResponse.serializer(),
                loginResponseString
            )

            viewModelScope.launch {
                _state.update {
                    it.copy(
                        loginResponseString = loginResponseString,
                        isSuccessGetSession = true,
                        loginResponse = newLoginResponse
                    )
                }
            }
        }


    }

    fun updateLoginResponse(newToken: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loginResponse = it.loginResponse?.copy(
                        jwtToken = newToken
                    )
                )
            }

        }
    }


    fun saveLoginReponseString(loginResponseString: String) {
        val loginResponse: LoginResponse = Json.decodeFromString(
            LoginResponse.serializer(),
            loginResponseString
        )

        viewModelScope.launch {

            _state.update {
                it.copy(
                    loginResponseString = loginResponseString,
                    loginResponse = loginResponse
                )
            }

        }


    }

}