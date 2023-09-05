package com.daffamuhtar.taskcm.approval.data.response

import kotlinx.serialization.Serializable

@Serializable
data class MenuX(
    val icon: String,
    val id: String,
    val isBeta: String,
    val link: String?,
    val name: String
)