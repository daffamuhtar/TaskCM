package com.daffamuhtar.taskcm.approval.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRefreshToken(
    @SerialName("jwtToken")
    val jwtToken: String,

    @SerialName("loggedUserId")
    val loggedUserId: String,

    @SerialName("message")
    val message: String,

    @SerialName("requiredUpdate")
    val requiredUpdate: Boolean,

    @SerialName("status")
    val status: Boolean
)