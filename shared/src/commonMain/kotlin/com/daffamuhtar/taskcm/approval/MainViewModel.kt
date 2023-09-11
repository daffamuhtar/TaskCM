package com.daffamuhtar.taskcm.approval

import com.daffamuhtar.taskcm.approval.data.response.LoginResponse
import com.daffamuhtar.taskcm.approval.utils.LoginState
import com.daffamuhtar.taskcm.approval.utils.RepairListState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())

    val state = _state.asStateFlow()


    fun saveLoginReponse(loginResponse: LoginResponse) {
        viewModelScope.launch {

            _state.update {
                it.copy(
                    loginResponse = loginResponse,
                    isSuccessGetSession = true
                )
            }

        }

    }

}