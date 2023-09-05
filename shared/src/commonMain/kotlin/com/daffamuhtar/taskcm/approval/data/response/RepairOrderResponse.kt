package com.daffamuhtar.taskcm.approval.data.response

import kotlinx.serialization.Serializable

@Serializable
data class RepairOrderResponse(
    val orderId: String,
    val SPKId: String?,
    val noteCheckFromMechanic: String,
    val offerId: String,
    val problemName: List<ProblemName>,
    val scheduledDate: String,
    val stageName: String,
    val totalAfterTax: String?,
    val vehicleBrand: String,
    val vehicleLicenseNumber: String,
    val vehiclePhoto: String,
    val vehicleType: String,
    val vehicleVarian: String,
    val vehicleYear: String,
    val vehicleName: String = "${vehicleBrand} ${vehicleType} ${vehicleVarian} ${vehicleYear}",
    val vehicleDistrict: String,
    val workshopArea: String,
    val workshopName: String
)