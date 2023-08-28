package com.daffamuhtar.taskcm.approval.data

import kotlinx.serialization.Serializable

@Serializable
data class DataPostRefreshToken(
    val userId :String,
    val currentAppVersion :String,
    val appType :String,
)
