package com.daffamuhtar.taskcm.approval.data

import kotlinx.serialization.Serializable

@Serializable
data class ResponseRefreshToken(
    val jwtToken: String,
    val loggedUserId: String,
    val message: String,
    val requiredUpdate: Boolean,
    val status: Boolean
)