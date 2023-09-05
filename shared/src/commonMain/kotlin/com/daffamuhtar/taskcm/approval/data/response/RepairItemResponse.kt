package com.daffamuhtar.taskcm.approval.data.response

data class RepairItemResponse(
    val orderId: String,
    val SPKId: String?,
    val noteCheckFromMechanic: String,
    val offerId: String,
    val problemName: List<ProblemNameResponse>,
    val scheduledDate: String,
    val stageName: String,
    val totalAfterTax: String,
    val vehicleBrand: String,
    val vehicleLicenseNumber: String,
    val vehiclePhoto: String,
    val vehicleType: String,
    val vehicleVarian: String,
    val vehicleYear: String,
    val vehicleLambungId: String?,
    val vehicleDistrict: String,
    val workshopArea: String,
    val workshopName: String
)