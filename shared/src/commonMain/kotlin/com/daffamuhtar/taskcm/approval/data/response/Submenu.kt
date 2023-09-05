package com.daffamuhtar.taskcm.approval.data.response

import kotlinx.serialization.Serializable

@Serializable
data class Submenu(
    val counter: String?,
    val id: String,
    val link: String?,
    val name: String,
    val submenu: List<SubmenuX>
)