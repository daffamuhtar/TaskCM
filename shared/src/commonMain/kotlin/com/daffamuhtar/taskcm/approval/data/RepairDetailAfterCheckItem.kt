package com.daffamuhtar.taskcm.approval.data

import kotlinx.serialization.Serializable

@Serializable
data class RepairDetailAfterCheckItem(
    val checkPhoto: List<CheckPhoto>,
    val noteCheckFromMechanic: String
)