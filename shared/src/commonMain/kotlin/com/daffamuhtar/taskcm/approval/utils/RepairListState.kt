package com.daffamuhtar.taskcm.approval.utils

import com.daffamuhtar.taskcm.approval.data.RepairDetailAfterCheckItem
import com.daffamuhtar.taskcm.approval.data.RepairDetailInfo
import com.daffamuhtar.taskcm.approval.data.RepairItem


data class RepairListState(
    val repairItemList : List<RepairItem> = emptyList(),
    val repairDetailInfoList : List<RepairDetailInfo> = emptyList(),

    val isLoadingGetRepairDetailInfoAdhoc: Boolean = false,
    val repairDetailInfo: RepairDetailInfo? = null,

    val isLoadingGetRepairDetailAfterCheck: Boolean = false,
    val repairDetailAfterCheckItems: List<RepairDetailAfterCheckItem> = emptyList(),

    val selectedContact: RepairItem? = null,
    val isAddContactSheetOpen: Boolean = false,
    val isSelectedContactSheetOpen: Boolean = false,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val emailError: String? = null,
    val phoneNumberError: String? = null,
)
