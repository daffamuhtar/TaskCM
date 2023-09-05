package com.daffamuhtar.taskcm.approval.data.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val status: Boolean,
    val message: String,
    val approvalLevel: String?,
    val companyName: String?,
    val defaultUrl: String?,
    val gaName: String?,
    val gaPhoto: String?,
    val jwtToken: String?,
    val loggedGAId: String?,
    val logged_in: Boolean?,
    val menus: List<Menu>?,
    val position: String?,
    val userLevel: String?
)