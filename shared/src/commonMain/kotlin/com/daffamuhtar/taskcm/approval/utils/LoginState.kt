package com.daffamuhtar.taskcm.approval.utils

import com.daffamuhtar.taskcm.approval.data.response.LoginResponse

data class LoginState(
    val loginResponse: LoginResponse? = null,
    val isSuccessGetSession :Boolean = false
)