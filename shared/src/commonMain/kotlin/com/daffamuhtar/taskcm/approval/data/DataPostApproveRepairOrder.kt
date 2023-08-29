package com.daffamuhtar.taskcm.approval.data

import kotlinx.serialization.Serializable

@Serializable
data class DataPostApproveRepairOrder(
    val loggedGAId :String,
    val offerId :String,
    val orderId :String,
)
