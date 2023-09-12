package com.daffamuhtar.taskcm.approval.utils

import com.daffamuhtar.taskcm.approval.data.RepairDetailAfterCheckItem
import com.daffamuhtar.taskcm.approval.data.RepairDetailInfo
import com.daffamuhtar.taskcm.approval.data.RepairDetailPartListItem
import com.daffamuhtar.taskcm.approval.data.RepairDetailPartTotalPrice
import com.daffamuhtar.taskcm.approval.data.model.RepairOrderModel
import com.daffamuhtar.taskcm.approval.data.ResponseResult
import com.daffamuhtar.taskcm.approval.data.response.LoginResponse

sealed interface RepairListEvent {
    object OnAddNewContactClick : RepairListEvent
    object DismissContact : RepairListEvent

    //login

    class PostLogin(val username: String, val password: String) : RepairListEvent
    data class DataLoginResponse(val loginResponse: LoginResponse) : RepairListEvent

 //======

    data class OnFirstNameChanged(val value: String) : RepairListEvent
    data class OnLastNameChanged(val value: String) : RepairListEvent
    data class OnEmailChanged(val value: String) : RepairListEvent
    data class OnPhoneNumberChanged(val value: String) : RepairListEvent
    class OnPhotoPicked(val bytes: ByteArray) : RepairListEvent
    object OnAddPhotoClicked : RepairListEvent
    object SaveContact : RepairListEvent
    data class SelectRepairItem(val repairOrderModel: RepairOrderModel) : RepairListEvent

    data class DoApproveRepairOrder(val repairOrderModel: RepairOrderModel) : RepairListEvent
    object DismissApproveRepairOrder : RepairListEvent
    data class DoRejectRepairOrder(val repairOrderModel: RepairOrderModel) : RepairListEvent
    object DismissRejectRepairOrder : RepairListEvent

    data class EditContact(val contact: RepairOrderModel) : RepairListEvent
    object DeleteContact : RepairListEvent


//    data class DataResponseRefreshToken(val responseRefreshToken: ResponseRefreshToken) : RepairListEvent

    class OnLoadingRepairOrderList(val int: Int) : RepairListEvent
    data class DataRepairOrderList(val repairOrderModels: List<RepairOrderModel>) : RepairListEvent

    object OnLoadingRepairDetailInfo : RepairListEvent
    data class DataRepairDetailInfo(val repairDetailInfo: RepairDetailInfo) : RepairListEvent

    object OnLoadingRepairDetailAfterCheck : RepairListEvent
    data class DataRepairDetailAfterCheck(val repairDetailAfterCheckItems: List<RepairDetailAfterCheckItem>) : RepairListEvent

    object OnLoadingRepairDetailPreviousPartList : RepairListEvent
    data class DataRepairDetailPreviousPartList(val repairDetailPartListItems: List<RepairDetailPartListItem>) : RepairListEvent

    object OnLoadingRepairDetailPreviousPartTotalPrice : RepairListEvent
    data class DataRepairDetailPreviousPartTotalPrice(val repairDetailPartTotalPrice: RepairDetailPartTotalPrice) : RepairListEvent

    object OnLoadingRepairDetailPartList : RepairListEvent
    data class DataRepairDetailPartList(val repairDetailPartListItems: List<RepairDetailPartListItem>) : RepairListEvent

    object OnLoadingRepairDetailPartTotalPrice : RepairListEvent
    data class DataRepairDetailPartTotalPrice(val repairDetailPartTotalPrice: RepairDetailPartTotalPrice) : RepairListEvent

    object OnLoadingRepairDetailWorkshopOfferNote: RepairListEvent
    data class DataRepairDetailWorkshopOfferNote(val repairDetailWorkshopOfferNote: String) : RepairListEvent

    class PostReject(val offerId: String, val orderId: String, val rejectionNote: String) : RepairListEvent
    data class DataResponseRejectRepairOrder(val responseResult: ResponseResult) : RepairListEvent

    class PostApproveRepairOrder(val offerId: String, val orderId: String) : RepairListEvent
    data class DataResponseApproveRepairOrder(val responseResult: ResponseResult) : RepairListEvent

}