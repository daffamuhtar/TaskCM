package com.daffamuhtar.taskcm.approval.utils

import com.daffamuhtar.taskcm.approval.data.RepairDetailAfterCheckItem
import com.daffamuhtar.taskcm.approval.data.RepairDetailInfo
import com.daffamuhtar.taskcm.approval.data.RepairDetailPartListItem
import com.daffamuhtar.taskcm.approval.data.RepairDetailPartTotalPrice
import com.daffamuhtar.taskcm.approval.data.RepairItem
import com.daffamuhtar.taskcm.approval.data.ResponseResult


data class RepairListState(
    val repairItemList : List<RepairItem> = emptyList(),
    val repairDetailInfoList : List<RepairDetailInfo> = emptyList(),

    val isLoadingGetRepairDetailInfoAdhoc: Boolean = false,
    val repairDetailInfo: RepairDetailInfo? = null,

    val isLoadingGetRepairDetailAfterCheck: Boolean = false,
    val repairDetailAfterCheckItems: List<RepairDetailAfterCheckItem> ? = null,

    val isLoadingGetRepairDetailPartList: Boolean = false,
    val repairDetailPartListItems: List<RepairDetailPartListItem> ? = null,

    val isLoadingGetRepairDetailPartTotalPrice: Boolean = false,
    val repairDetailPartTotalPrice: RepairDetailPartTotalPrice? = null,

    val isLoadingGetRepairDetailWorkshopOfferNote: Boolean = false,
    val repairDetailWorkshopOfferNote: String? = null,

    val isLoadingPostRejectRepairOrder: Boolean = false,
    val rejectRepairOrderResponseResult: ResponseResult? = null,

    val isLoadingPostApproveRepairOrder: Boolean = false,
    val approveRepairOrderResponseResult: ResponseResult? = null,


    val selectedContact: RepairItem? = null,
    val isAddContactSheetOpen: Boolean = false,
    val isSelectedContactSheetOpen: Boolean = false,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val emailError: String? = null,
    val phoneNumberError: String? = null,
)
