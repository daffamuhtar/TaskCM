package com.daffamuhtar.taskcm.approval.data

import kotlinx.serialization.Serializable

@Serializable
data class RepairDetailPartTotalPrice(
    val after_tax: String,
    val discount: String,
    val subTotal: String,
    val subTotalAfterDiscount: String,
    val tax: String,
    val taxpph23: String
)