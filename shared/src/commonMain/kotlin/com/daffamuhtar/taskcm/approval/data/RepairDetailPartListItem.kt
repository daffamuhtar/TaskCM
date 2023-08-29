package com.daffamuhtar.taskcm.approval.data

import kotlinx.serialization.Serializable

@Serializable
data class RepairDetailPartListItem(
    val genuineOrNon: String?,
    val itemBrand: String?,
    val partCategory: String?,
    val partCategoryId: String?,
    val partIdFromFleetify: String,
    val partIdFromWorkshop: String,
    val partName: String,
    val partPrice: String,
    val partQuantity: String,
    val partSKU: String,
    val partTotalPrice: String,
    val unit: String?,
    val vehicleBrand: String?,
    val vehicleType: String?,
    val vehicleTypeId: String?
)