package com.daffamuhtar.taskcm.approval.utils

import com.daffamuhtar.taskcm.approval.data.RepairDetailAfterCheckItem
import com.daffamuhtar.taskcm.approval.data.RepairDetailInfo
import com.daffamuhtar.taskcm.approval.data.RepairDetailPartListItem
import com.daffamuhtar.taskcm.approval.data.RepairDetailPartTotalPrice
import com.daffamuhtar.taskcm.approval.data.model.RepairOrderModel
import com.daffamuhtar.taskcm.approval.data.ResponseResult
import com.daffamuhtar.taskcm.approval.data.response.LoginResponse


data class RepairListState(


    val isLoadingPostLogin: Boolean = false,
    val loginResponse: LoginResponse? = null,

    val loggedUserId: String? = loginResponse?.loggedGAId ?: "GA-BLOG-3",
//    val token: String = "GA-BLOG-2",
//    val loggedUserId: String = "GA-BLOG-2",

//    ==========================
    val repairOrderModelList: List<RepairOrderModel> = emptyList(),

    val isLoadingRepairOrderList: Boolean = false,
    val repairOrderModels: List<RepairOrderModel>? = null,

    //============

    val isLoadingGetRepairDetailInfoAdhoc: Boolean = false,
    val repairDetailInfo: RepairDetailInfo? = null,

    val isLoadingGetRepairDetailAfterCheck: Boolean = false,
    val repairDetailAfterCheckItems: List<RepairDetailAfterCheckItem>? = null,

    val isLoadingGetRepairDetailPartList: Boolean = false,
    val repairDetailPartListItems: List<RepairDetailPartListItem>? = null,

    val isLoadingGetRepairDetailPartTotalPrice: Boolean = false,
    val repairDetailPartTotalPrice: RepairDetailPartTotalPrice? = null,

    val isLoadingGetRepairDetailWorkshopOfferNote: Boolean = false,
    val repairDetailWorkshopOfferNote: String? = null,

    val isLoadingPostRejectRepairOrder: Boolean = false,
    val rejectRepairOrderResponseResult: ResponseResult? = null,

    val isLoadingPostApproveRepairOrder: Boolean = false,
    val approveRepairOrderResponseResult: ResponseResult? = null,

    val selectedRepairOrderModel: RepairOrderModel? = null,
    val isAddContactSheetOpen: Boolean = false,

    val isSelectedContactSheetOpen: Boolean = false,

    val isApproveConfirmationSheetOpen: Boolean = false,

    val isRejectConfirmationSheetOpen: Boolean = false,


    val rejectionNoteError: String? = null,

    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val emailError: String? = null,
    val phoneNumberError: String? = null,
)
