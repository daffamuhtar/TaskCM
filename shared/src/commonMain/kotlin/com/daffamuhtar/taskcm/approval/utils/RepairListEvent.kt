package com.daffamuhtar.taskcm.approval.utils

import com.daffamuhtar.taskcm.approval.component.RepairDetailInfoItem
import com.daffamuhtar.taskcm.approval.data.RepairDetailAfterCheckItem
import com.daffamuhtar.taskcm.approval.data.RepairDetailInfo
import com.daffamuhtar.taskcm.approval.data.RepairItem

sealed interface RepairListEvent {
    object OnAddNewContactClick : RepairListEvent
    object DismissContact : RepairListEvent
    data class OnFirstNameChanged(val value: String) : RepairListEvent
    data class OnLastNameChanged(val value: String) : RepairListEvent
    data class OnEmailChanged(val value: String) : RepairListEvent
    data class OnPhoneNumberChanged(val value: String) : RepairListEvent
    class OnPhotoPicked(val bytes: ByteArray) : RepairListEvent
    object OnAddPhotoClicked : RepairListEvent
    object SaveContact : RepairListEvent
    data class SelectRepairItem(val contact: RepairItem) : RepairListEvent
    data class EditContact(val contact: RepairItem) : RepairListEvent
    object DeleteContact : RepairListEvent

    object OnLoadingRepairDetailInfo : RepairListEvent
    data class DataRepairDetailInfo(val repairDetailInfo: RepairDetailInfo) : RepairListEvent

    object OnLoadingRepairDetailAfterCheck : RepairListEvent
    data class DataRepairDetailAfterCheck(val repairDetailAfterCheckItems: List<RepairDetailAfterCheckItem>) : RepairListEvent

}