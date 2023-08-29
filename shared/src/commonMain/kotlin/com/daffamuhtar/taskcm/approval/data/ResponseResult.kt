package com.daffamuhtar.taskcm.approval.data

import kotlinx.serialization.Serializable

@Serializable
data class ResponseResult(
    val status: Boolean,
    val message: String,
)