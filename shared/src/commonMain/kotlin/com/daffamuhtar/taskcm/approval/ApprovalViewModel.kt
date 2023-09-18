package com.daffamuhtar.taskcm.approval

import androidx.compose.material3.SnackbarHostState
import com.daffamuhtar.taskcm.approval.data.DataPostApproveRepairOrder
import com.daffamuhtar.taskcm.approval.data.DataPostLogin
import com.daffamuhtar.taskcm.approval.data.DataPostRefreshToken
import com.daffamuhtar.taskcm.approval.data.DataPostRejectRepairOrder
import com.daffamuhtar.taskcm.approval.data.RepairDetailAfterCheckItem
import com.daffamuhtar.taskcm.approval.data.RepairDetailInfo
import com.daffamuhtar.taskcm.approval.data.RepairDetailPartListItem
import com.daffamuhtar.taskcm.approval.data.RepairDetailPartTotalPrice
import com.daffamuhtar.taskcm.approval.data.model.RepairOrderModel
import com.daffamuhtar.taskcm.approval.data.ResponseRefreshToken
import com.daffamuhtar.taskcm.approval.data.ResponseResult
import com.daffamuhtar.taskcm.approval.data.response.LoginResponse
import com.daffamuhtar.taskcm.approval.data.response.RepairOrderAdditionalPartRequestResponse
import com.daffamuhtar.taskcm.approval.data.response.RepairOrderResponse
import com.daffamuhtar.taskcm.approval.utils.LoginState
import com.daffamuhtar.taskcm.approval.utils.RepairListEvent
import com.daffamuhtar.taskcm.approval.utils.RepairListState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

//data class ApprovalUiState(
//    val repairOrderModels: List<RepairOrderModel> = emptyList(),
//    val selectedCategory: String? = null,
//    var isShowDetail: Boolean = false
//) {
//    //    val categories = images.map { it.problemStage }.toSet()
////    val selectedImages = images.filter { it.problemStage == selectedCategory }
//    var valueShowDetail = isShowDetail
//}

class ApprovalViewModel(
    loggedUserId: String?,
    userToken: String?,
    val mainViewModel: MainViewModel?,
    val mainState: LoginState?,
    val snackbarHostState: SnackbarHostState,
) : ViewModel()
{

    //    private val _uiState = MutableStateFlow<ApprovalUiState>(ApprovalUiState())
    private val _state = MutableStateFlow(RepairListState())

    val state = _state.asStateFlow()

    var realUserId = loggedUserId

    fun onEvent(event: RepairListEvent) {
        when (event) {

            RepairListEvent.DismissContact -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isSelectedContactSheetOpen = false,
                            isAddContactSheetOpen = false,
                            firstNameError = null,
                            lastNameError = null,
                            emailError = null,
                            phoneNumberError = null,
                        )
                    }
                    delay(300L) // Animation delay
                    _state.update {
                        it.copy(
                            selectedRepairOrderModel = null,
                            repairDetailInfo = null,
                            repairDetailAfterCheckItems = null,
                            repairDetailPreviousPartListItems = null,
                            repairDetailPreviousPartTotalPrice = null,
                            repairDetailPartListItems = null,
                            repairDetailPartTotalPrice = null,
                            repairDetailWorkshopOfferNote = null,
                            approveRepairOrderResponseResult = null,
                            isApproveConfirmationSheetOpen = false,
                        )
                    }
                }
            }


            is RepairListEvent.SelectRepairItem -> {
                _state.update {
                    it.copy(
                        selectedRepairOrderModel = event.repairOrderModel,
                        isSelectedContactSheetOpen = true
                    )
                }
            }

            is RepairListEvent.DoApproveRepairOrder -> {
                _state.update {
                    it.copy(
                        isApproveConfirmationSheetOpen = true
                    )
                }
            }

            is RepairListEvent.DismissApproveRepairOrder -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isApproveConfirmationSheetOpen = false,
                        )
                    }
                }
            }

            is RepairListEvent.DoRejectRepairOrder -> {
                _state.update {
                    it.copy(
                        isRejectConfirmationSheetOpen = true
                    )
                }
            }

            is RepairListEvent.DismissRejectRepairOrder -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isRejectConfirmationSheetOpen = false,
                        )
                    }
                }
            }

            is RepairListEvent.OnLoadingRepairOrderList -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoadingRepairOrderList = true,
                            repairOrderModels = null
                        )
                    }

                    var result: List<RepairOrderModel> = emptyList()
                    _state.value.loginResponse?.loggedGAId?.let {
                        when (event.int) {
                            0 -> {
                                result = getRepairOrderAdhoc(it)
                            }

                            1 -> {

                                result =
                                    getRepairOrderAdditionalPartReqeustAdhoc(
                                        it,
                                        null
                                    )
                            }

                            2 -> {
                                result = getRepairOrderAdhoc(it)
                            }

                            else -> {
                                result = getRepairOrderAdhoc(it)
                            }
                        }
                    }

                    onEvent(RepairListEvent.DataRepairOrderList(result))

                }
            }

            is RepairListEvent.DataRepairOrderList -> {
                _state.update {
                    it.copy(
                        isLoadingRepairOrderList = false,
                        repairOrderModels = event.repairOrderModels
                    )
                }
            }

            is RepairListEvent.OnLoadingRepairDetailInfo -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoadingGetRepairDetailInfoAdhoc = true
                        )
                    }
                    _state.value.selectedRepairOrderModel?.let {
                        val repairDetailInfo = getRepairDetailInfoAdhoc(it.orderId)[0]
                        onEvent(RepairListEvent.DataRepairDetailInfo(repairDetailInfo))
                    } ?: run {
                        _state.update {
                            it.copy(
                                isLoadingGetRepairDetailInfoAdhoc = false
                            )
                        }
                    }


                }
            }

            is RepairListEvent.DataRepairDetailInfo -> {
                _state.update {
                    it.copy(
                        isLoadingGetRepairDetailInfoAdhoc = false,
                        repairDetailInfo = event.repairDetailInfo
                    )
                }
            }

            is RepairListEvent.OnLoadingRepairDetailAfterCheck -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoadingGetRepairDetailAfterCheck = true
                        )
                    }

                    _state.value.selectedRepairOrderModel?.let {
                        val repairDetailAfterCheckItems = getRepairDetailAfterCheck(it.orderId)
                        onEvent(
                            RepairListEvent.DataRepairDetailAfterCheck(
                                repairDetailAfterCheckItems
                            )
                        )

                    } ?: run {
                        _state.update {
                            it.copy(
                                isLoadingGetRepairDetailAfterCheck = false
                            )
                        }
                    }

                }
            }


            is RepairListEvent.DataRepairDetailAfterCheck -> {
                _state.update {
                    it.copy(
                        isLoadingGetRepairDetailAfterCheck = false,
                        repairDetailAfterCheckItems = event.repairDetailAfterCheckItems
                    )
                }
            }

            // Previous Correction

            is RepairListEvent.OnLoadingRepairDetailPreviousPartList -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoadingGetRepairDetailPreviousPartList = true
                        )
                    }
                    _state.value.selectedRepairOrderModel?.let {
                        val repairDetailPartListItems = getRepairDetailPartList(it.offerId)

                        onEvent(RepairListEvent.DataRepairDetailPreviousPartList(repairDetailPartListItems))

                    } ?: run {
                        _state.update {
                            it.copy(
                                isLoadingGetRepairDetailPreviousPartList = false
                            )
                        }
                    }


                }
            }

            is RepairListEvent.DataRepairDetailPreviousPartList -> {
                _state.update {
                    it.copy(
                        isLoadingGetRepairDetailPreviousPartList = false,
                        repairDetailPreviousPartListItems = event.repairDetailPartListItems
                    )
                }
            }

            is RepairListEvent.OnLoadingRepairDetailPreviousPartTotalPrice -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoadingGetRepairDetailPreviousPartTotalPrice = true
                        )
                    }

                    _state.value.selectedRepairOrderModel?.let {
                        val repairDetailPartListItems = getRepairDetailPartTotalPrice(it.offerId)

                        onEvent(
                            RepairListEvent.DataRepairDetailPreviousPartTotalPrice(
                                repairDetailPartListItems
                            )
                        )

                    } ?: run {
                        _state.update {
                            it.copy(
                                isLoadingGetRepairDetailPreviousPartTotalPrice = false
                            )
                        }
                    }

                }
            }

            is RepairListEvent.DataRepairDetailPreviousPartTotalPrice -> {
                _state.update {
                    it.copy(
                        isLoadingGetRepairDetailPreviousPartTotalPrice = false,
                        repairDetailPreviousPartTotalPrice = event.repairDetailPartTotalPrice
                    )
                }
            }

            //After correction

            is RepairListEvent.OnLoadingRepairDetailPartList -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoadingGetRepairDetailPartList = true
                        )
                    }
                    _state.value.selectedRepairOrderModel?.let {
                        val repairDetailPartListItems = getRepairDetailPartList(it.offerId)

                        onEvent(RepairListEvent.DataRepairDetailPartList(repairDetailPartListItems))

                    } ?: run {
                        _state.update {
                            it.copy(
                                isLoadingGetRepairDetailPartList = false
                            )
                        }
                    }


                }
            }

            is RepairListEvent.DataRepairDetailPartList -> {
                _state.update {
                    it.copy(
                        isLoadingGetRepairDetailPartList = false,
                        repairDetailPartListItems = event.repairDetailPartListItems
                    )
                }
            }

            is RepairListEvent.OnLoadingRepairDetailPartTotalPrice -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoadingGetRepairDetailPartTotalPrice = true
                        )
                    }

                    _state.value.selectedRepairOrderModel?.let {
                        val repairDetailPartListItems = getRepairDetailPartTotalPrice(it.offerId)

                        onEvent(
                            RepairListEvent.DataRepairDetailPartTotalPrice(
                                repairDetailPartListItems
                            )
                        )

                    } ?: run {
                        _state.update {
                            it.copy(
                                isLoadingGetRepairDetailPartTotalPrice = false
                            )
                        }
                    }

                }
            }


            is RepairListEvent.DataRepairDetailPartTotalPrice -> {
                _state.update {
                    it.copy(
                        isLoadingGetRepairDetailPartTotalPrice = false,
                        repairDetailPartTotalPrice = event.repairDetailPartTotalPrice
                    )
                }
            }

            is RepairListEvent.OnLoadingRepairDetailWorkshopOfferNote -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoadingGetRepairDetailWorkshopOfferNote = true
                        )
                    }
//
//                    _state.value.selectedRepairOrderModel?.let {
//                        val repairDetailWorkshopOfferNote =
//                            getRepairDetailWorkshopOfferNote(it.orderId)
//
//                        onEvent(
//                            RepairListEvent.DataRepairDetailWorkshopOfferNote(
//                                repairDetailWorkshopOfferNote
//                            )
//                        )
//                    } ?: run {
//                        _state.update {
//                            it.copy(
//                                isLoadingGetRepairDetailPartTotalPrice = false
//                            )
//                        }
//                    }
                }
            }


            is RepairListEvent.DataRepairDetailWorkshopOfferNote -> {
                _state.update {
                    it.copy(
                        isLoadingGetRepairDetailWorkshopOfferNote = false,
                        repairDetailWorkshopOfferNote = event.repairDetailWorkshopOfferNote
                    )
                }
            }

            is RepairListEvent.PostReject -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoadingPostRejectRepairOrder = true
                        )
                    }

//                    val response = rejectRepair(
//                        userId = _state.value.loggedUserId,
//                        offerId = event.offerId,
//                        orderId = event.orderId,
//                        rejectionNote = event.rejectionNote
//                    )
//                    onEvent(RepairListEvent.DataResponseApproveRepairOrder(response))

                    delay(3000) // Animation delay

                    onEvent(
                        RepairListEvent.DataResponseRejectRepairOrder(
                            responseResult = ResponseResult(
                                true,
                                "message"
                            )
                        )
                    )

                }
            }

            is RepairListEvent.DataResponseRejectRepairOrder -> {
                _state.update {
                    it.copy(
                        isSelectedContactSheetOpen = false,
                        selectedRepairOrderModel = null,
                        isLoadingPostRejectRepairOrder = false,
                        isRejectConfirmationSheetOpen = false,
                        rejectRepairOrderResponseResult = event.responseResult
                    )
                }
            }

            is RepairListEvent.PostApproveRepairOrder -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoadingPostApproveRepairOrder = true
                        )
                    }

//                    _state.value.selectedRepairOrderModel?.let {
//                        val response = approveRepairOrder(
//                            userId = _state.value.loggedUserId,
//                            offerId = event.offerId,
//                            orderId = event.orderId,
//                        )
//
//                        onEvent(RepairListEvent.DataResponseApproveRepairOrder(response))
//
//                    } ?: run {
//                        _state.update {
//                            it.copy(
//                                isLoadingGetRepairDetailPartTotalPrice = false
//                            )
//                        }
//                    }

                    delay(3000) // Animation delay

                    onEvent(
                        RepairListEvent.DataResponseApproveRepairOrder(
                            responseResult = ResponseResult(
                                true,
                                "message"
                            )
                        )
                    )


                }
            }

            is RepairListEvent.DataResponseApproveRepairOrder -> {

                _state.update {
                    it.copy(
                        isSelectedContactSheetOpen = false,
                        selectedRepairOrderModel = null,
                        isLoadingPostApproveRepairOrder = false,
                        isApproveConfirmationSheetOpen = false,
                        approveRepairOrderResponseResult = event.responseResult
                    )
                }
            }

            is RepairListEvent.PostLogin -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoadingPostLogin = true
                        )
                    }

                    val response = login(
                        username = event.username,
                        password = event.password,
                    )

                    onEvent(RepairListEvent.DataLoginResponse(response.body<LoginResponse>(),response.body<String>()))

//                    delay(3000) // Animation delay
//
//                    onEvent(
//                        RepairListEvent.DataResponseRejectRepairOrder(
//                            responseResult = ResponseResult(
//                                true,
//                                "message"
//                            )
//                        )
//                    )

                }
            }

            is RepairListEvent.DataLoginResponse -> {

                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoadingPostLogin = false,
                            loginResponse = event.loginResponse,
                        )
                    }

                    delay(300L)

                    mainViewModel?.let {
                        mainViewModel.saveLoginReponse(
                            loginResponse =  event.loginResponse,
                            loginResponseString = event.loginResponseString
                        )
                    }

                    onEvent(RepairListEvent.OnLoadingRepairOrderList(0))

                }


            }

            else -> Unit
        }
    }


    //    val uiState = _uiState.asStateFlow()

    private val httpClient = HttpClient {

        install(HttpTimeout) {
            requestTimeoutMillis = 10000
            connectTimeoutMillis = 10000
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
                prettyPrint = true
                isLenient = true
            })

        }

        install(Auth) {
            val token = state.value.loginResponse?.jwtToken ?: "no token"

            bearer {
                refreshTokens { // this: RefreshTokensParams
                    // Refresh tokens and return them as the 'BearerTokens' instance
                    _state.value.loginResponse?.jwtToken?.let {
                        BearerTokens(
                            accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjIiLCJ1c2VybmFtZSI6InVtLXRlc3RpbmctOTk5OSIsImlhdCI6MTY5NTAyMjAxMCwiZXhwIjoxNjk1MTA4NDEwfQ.e7B08iXzoBXaifaSkJzM2bg1P10EKbStqQpfK0w5GXw",
                            refreshToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjIiLCJ1c2VybmFtZSI6InVtLXRlc3RpbmctOTk5OSIsImlhdCI6MTY5NTAyMjAxMCwiZXhwIjoxNjk1MTA4NDEwfQ.e7B08iXzoBXaifaSkJzM2bg1P10EKbStqQpfK0w5GXw"
                        )
                    }
                }


                loadTokens {
                    _state.value.loginResponse?.jwtToken?.let {
                        BearerTokens(
                            accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjIiLCJ1c2VybmFtZSI6InVtLXRlc3RpbmctOTk5OSIsImlhdCI6MTY5NTAyMjAxMCwiZXhwIjoxNjk1MTA4NDEwfQ.e7B08iXzoBXaifaSkJzM2bg1P10EKbStqQpfK0w5GXw",
                            refreshToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjIiLCJ1c2VybmFtZSI6InVtLXRlc3RpbmctOTk5OSIsImlhdCI6MTY5NTAyMjAxMCwiZXhwIjoxNjk1MTA4NDEwfQ.e7B08iXzoBXaifaSkJzM2bg1P10EKbStqQpfK0w5GXw"
                        )
                    }

                }

            }
        }

    }

    init {

        viewModelScope.launch {
            val refreshTokenResponse : ResponseRefreshToken = refreshToken()

            mainViewModel?.state?.value?.loginResponseString?.let {
                mainViewModel.updateLoginResponse(refreshTokenResponse.jwtToken)

            }



            mainViewModel?.state?.value?.loginResponse?.let {

                _state.update { state ->
                    state.copy(
                        loginResponse = it
                    )
                }
            } ?: run {


                mainViewModel?.state?.value?.loginResponseString?.let {

                    val newLoginResponse: LoginResponse = Json.decodeFromString(
                        LoginResponse.serializer(),
                        it
                    )

                    _state.update { state ->
                        state.copy(
                            loginResponse = newLoginResponse
                        )
                    }
                }
            }

            _state.value.loginResponse?.let {
                onEvent(RepairListEvent.OnLoadingRepairOrderList(0))
            }
        }

    }

    override fun onCleared() {
        httpClient.close()
    }

//    fun selectCategory(category: String) {
//        _uiState.update {
//            it.copy(selectedCategory = category)
//        }
//    }
//
//    fun showDetail(show: Boolean) {
//        _uiState.update {
//            it.copy(isShowDetail = show)
//        }
//    }
//
//    fun updateImages() {
//        viewModelScope.launch {
//            val images = getRepairOrderAdhoc()
//            _uiState.update {
//                it.copy(repairOrderModels = images)
//            }
//        }
//    }

    suspend fun login(
        username: String,
        password: String,
    ): HttpResponse {
        val response = httpClient
            .post("https://api-staging-v10.fleetify.id/api/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(
                    DataPostLogin(
                        username = username,
                        password = password,
                    )
                )
            }

        return response
    }

    suspend fun refreshToken(): ResponseRefreshToken {
        val response: ResponseRefreshToken = httpClient
            .post("https://api-staging-v10.fleetify.id/api/auth/refresh_token") {
                contentType(ContentType.Application.Json)
                setBody(DataPostRefreshToken("UM-BLOG-9999", "1104", "mechanic"))
            }.body()

        return response
    }

    suspend fun rejectRepair(
        userId: String,
        offerId: String,
        orderId: String,
        rejectionNote: String
    ): ResponseResult {
        val response = httpClient
            .put("https://api-staging-v10.fleetify.id/api/orders/not_approve_order_any_level") {
                contentType(ContentType.Application.Json)
                setBody(
                    DataPostRejectRepairOrder(
                        loggedGAId = userId,
                        offerId = offerId,
                        orderId = orderId,
                        rejectionNote = rejectionNote
                    )
                )
            }.body<ResponseResult>()

        return response
    }

    suspend fun approveRepairOrder(
        userId: String,
        offerId: String,
        orderId: String
    ): ResponseResult {
        val response = httpClient
            .put("https://api-staging-v10.fleetify.id/api/orders/approve_level_1") {
                contentType(ContentType.Application.Json)
                setBody(
                    DataPostApproveRepairOrder(
                        loggedGAId = userId,
                        offerId = offerId,
                        orderId = orderId,
                    )
                )
            }.body<ResponseResult>()

        return response
    }

    suspend fun getRepairOrderAdhoc(userId: String): List<RepairOrderModel> {
        val result = httpClient
            .get("https://api-staging-v10.fleetify.id/api/orders/waiting_approval_level_1?loggedGAId=${userId}")

        return if (result.status == HttpStatusCode.OK) {
            setResultRepairOrder(result.body<List<RepairOrderResponse>>())

        } else {

            setResultRepairOrder(emptyList())

        }


    }

    suspend fun getRepairOrderAdditionalPartReqeustAdhoc(
        userId: String,
        query: String?
    ): List<RepairOrderModel> {
        val result = httpClient
            .get("https://api-staging-v10.fleetify.id/api/orders/waiting_approval_level_1_correction_offer?loggedGAId=${userId}&query=${query}")

        return if (result.status == HttpStatusCode.OK) {
            setResultRepairOrderAdditionalPartRequest(result.body<List<RepairOrderAdditionalPartRequestResponse>>())

        } else {

            setResultRepairOrder(emptyList())

        }
    }

    suspend fun getRepairDetailInfoAdhoc(orderId: String): List<RepairDetailInfo> {
        val detailOrder = httpClient
            .get("https://api-staging-v10.fleetify.id/api/orders/order_info?orderId=${orderId}")
            .body<List<RepairDetailInfo>>()
        return detailOrder
    }

    suspend fun getRepairDetailAfterCheck(orderId: String): List<RepairDetailAfterCheckItem> {
        val repairDetailAfterCheckItems = httpClient
            .get("https://api-staging-v10.fleetify.id/api/orders/check_result_photos?orderId=${orderId}")
            .body<List<RepairDetailAfterCheckItem>>()
        return repairDetailAfterCheckItems
    }

    suspend fun getRepairDetailPartList(offerId: String): List<RepairDetailPartListItem> {
        val repairDetailPartList = httpClient
            .get("https://api-staging-v10.fleetify.id/api/orders/offer_detail_by_offer_id?offerId=${offerId}")
            .body<List<RepairDetailPartListItem>>()
        return repairDetailPartList
    }

    suspend fun getRepairDetailPartTotalPrice(offerId: String): RepairDetailPartTotalPrice {
        val repairDetailPartTotalPrice = httpClient
            .get("https://api-staging-v10.fleetify.id/api/orders/offer_price_by_offer_id?offerId=${offerId}")
            .body<List<RepairDetailPartTotalPrice>>()
        return repairDetailPartTotalPrice[0]
    }

    suspend fun getRepairDetailWorkshopOfferNote(orderId: String): String {
        val workshopOfferNote = httpClient
            .get("https://api-staging-v10.fleetify.id/api/orders/offer_letter_note_from_workshop?orderId=${orderId}")
            .body<String>()
        return workshopOfferNote


    }

    fun showSnackbar(message: String) {
        viewModelScope.launch {
            snackbarHostState.showSnackbar(message, withDismissAction = true)

        }

    }


    private fun setResultRepairOrder(repairs: List<RepairOrderResponse>): ArrayList<RepairOrderModel> {

        val listReport = ArrayList<RepairOrderModel>()

        for (repair in repairs) {
            repair.apply {
                val getResult = RepairOrderModel(
                    orderId = orderId,
                    SPKId = SPKId,
                    noteCheckFromMechanic = noteCheckFromMechanic,
                    offerId = offerId,
                    previousOfferId = null,
                    correctionReason = null,
                    problemName = problemName,
                    scheduledDate = scheduledDate,
                    stageName = stageName,
                    totalAfterTax = totalAfterTax,
                    vehicleBrand = vehicleBrand,
                    vehicleLicenseNumber = vehicleLicenseNumber,
                    vehiclePhoto = vehiclePhoto,
                    vehicleType = vehicleType,
                    vehicleVarian = vehicleVarian,
                    vehicleYear = vehicleYear,
                    vehicleName = vehicleName,
                    vehicleDistrict = vehicleDistrict,
                    workshopArea = workshopArea,
                    workshopName = workshopName
                )
                listReport.add(getResult)
            }
        }
        return listReport
    }

    private fun setResultRepairOrderAdditionalPartRequest(repairs: List<RepairOrderAdditionalPartRequestResponse>): ArrayList<RepairOrderModel> {


        val listReport = ArrayList<RepairOrderModel>()

        for (repair in repairs) {
            repair.apply {
                val getResult = RepairOrderModel(
                    orderId = orderId,
                    SPKId = null,
                    noteCheckFromMechanic = null,
                    offerId = offerId,
                    previousOfferId = previousOfferId,
                    correctionReason = correctionReason,
                    problemName = problemName,
                    scheduledDate = scheduledDate,
                    stageName = stageName,
                    totalAfterTax = null,
                    vehicleBrand = vehicleBrand,
                    vehicleLicenseNumber = vehicleLicenseNumber,
                    vehiclePhoto = vehiclePhoto,
                    vehicleType = vehicleType,
                    vehicleVarian = vehicleVarian,
                    vehicleYear = vehicleYear,
                    vehicleName = vehicleName,
                    vehicleDistrict = vehicleDistrict,
                    workshopArea = workshopArea,
                    workshopName = workshopName
                )
                listReport.add(getResult)
            }
        }
        return listReport
    }

}