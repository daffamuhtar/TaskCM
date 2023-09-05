package com.daffamuhtar.taskcm.approval.data

import kotlinx.serialization.Serializable

@Serializable
data class DataPostLogin(
    val username :String,
    val password :String,
)
