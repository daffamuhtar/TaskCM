package com.daffamuhtar.taskcm.approval.data.response

import kotlinx.serialization.Serializable

@Serializable
data class Menu(
    val menu: MenuX,
    val submenu: List<Submenu>
)