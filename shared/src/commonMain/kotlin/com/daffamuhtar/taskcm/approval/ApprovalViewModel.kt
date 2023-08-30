package com.daffamuhtar.taskcm.approval

import com.daffamuhtar.taskcm.approval.data.DataPostApproveRepairOrder
import com.daffamuhtar.taskcm.approval.data.DataPostRefreshToken
import com.daffamuhtar.taskcm.approval.data.DataPostRejectRepairOrder
import com.daffamuhtar.taskcm.approval.data.RepairDetailAfterCheckItem
import com.daffamuhtar.taskcm.approval.data.RepairDetailInfo
import com.daffamuhtar.taskcm.approval.data.RepairDetailPartListItem
import com.daffamuhtar.taskcm.approval.data.RepairDetailPartTotalPrice
import com.daffamuhtar.taskcm.approval.data.model.RepairOrderModel
import com.daffamuhtar.taskcm.approval.data.ResponseRefreshToken
import com.daffamuhtar.taskcm.approval.data.ResponseResult
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
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

data class ApprovalUiState(
    val images: List<RepairOrderModel> = emptyList(),
    val selectedCategory: String? = null,
    var isShowDetail: Boolean = false
) {
    //    val categories = images.map { it.problemStage }.toSet()
//    val selectedImages = images.filter { it.problemStage == selectedCategory }
    var valueShowDetail = isShowDetail
}

class ApprovalViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow<ApprovalUiState>(ApprovalUiState())
    private val _state = MutableStateFlow(RepairListState())

    val state = _state.asStateFlow()

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
                            phoneNumberError = null
                        )
                    }
                    delay(300L) // Animation delay
                    _state.update {
                        it.copy(
                            selectedContact = null
                        )
                    }
                }
            }


            is RepairListEvent.SelectRepairItem -> {
                _state.update {
                    it.copy(
                        selectedContact = event.contact,
                        isSelectedContactSheetOpen = true
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
                    val repairDetailInfo = getRepairDetailInfoAdhoc()[0]

                    onEvent(RepairListEvent.DataRepairDetailInfo(repairDetailInfo))

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
                    val repairDetailAfterCheckItems = getRepairDetailAfterCheck()

                    onEvent(RepairListEvent.DataRepairDetailAfterCheck(repairDetailAfterCheckItems))

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

            is RepairListEvent.OnLoadingRepairDetailPartList -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoadingGetRepairDetailPartList = true
                        )
                    }
                    val repairDetailPartListItems = getRepairDetailPartList()

                    onEvent(RepairListEvent.DataRepairDetailPartList(repairDetailPartListItems))

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
                    val repairDetailPartListItems = getRepairDetailPartTotalPrice()

                    onEvent(RepairListEvent.DataRepairDetailPartTotalPrice(repairDetailPartListItems))

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
                    val repairDetailWorkshopOfferNote = getRepairDetailWorkshopOfferNote()

                    onEvent(
                        RepairListEvent.DataRepairDetailWorkshopOfferNote(
                            repairDetailWorkshopOfferNote
                        )
                    )

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

                    val response = rejectRepair(
                        offerId = event.offerId,
                        orderId = event.orderId
                    )

                    onEvent(RepairListEvent.DataResponseRejectRepairOrder(response))

                }
            }

            is RepairListEvent.DataResponseRejectRepairOrder -> {
                _state.update {
                    it.copy(
                        isLoadingPostRejectRepairOrder = false,
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

                    val response = approveRepairOrder(
                        offerId = event.offerId,
                        orderId = event.orderId
                    )

                    onEvent(RepairListEvent.DataResponseApproveRepairOrder(response))

                }
            }

            is RepairListEvent.DataResponseApproveRepairOrder -> {
                _state.update {
                    it.copy(
                        isLoadingPostApproveRepairOrder = false,
                        approveRepairOrderResponseResult = event.responseResult
                    )
                }
            }

            else -> Unit
        }
    }


    val uiState = _uiState.asStateFlow()
    var token =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjIiLCJ1c2VybmFtZSI6IlVNLUJMT0ctOTk5OSIsImlhdCI6MTY5MzM2Mzc4NSwiZXhwIjoxNjkzNDUwMTg1fQ.9AIB0A2Xz8Zb3iMz2WgUglpWk6BWKv84QvFRRS0wntc"

    private val httpClient = HttpClient {

        install(HttpTimeout) {
            requestTimeoutMillis = 100000
            connectTimeoutMillis = 100000
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
            bearer {
                refreshTokens { // this: RefreshTokensParams
                    // Refresh tokens and return them as the 'BearerTokens' instance
                    BearerTokens(accessToken = token, refreshToken = token)
                }
            }
        }

    }

    init {
        updateImages()
    }

    override fun onCleared() {
        httpClient.close()
    }

    fun selectCategory(category: String) {
        _uiState.update {
            it.copy(selectedCategory = category)
        }
    }

    fun showDetail(show: Boolean) {
        _uiState.update {
            it.copy(isShowDetail = show)
        }
    }

    fun updateImages() {
        viewModelScope.launch {
            val images = getRepairApprovalAdhoc()
            _uiState.update {
                it.copy(images = images)
            }
        }
    }

    suspend fun refreshToken(): ResponseRefreshToken {
        val images: ResponseRefreshToken = httpClient
            .post("https://api-staging-v10.fleetify.id/api/auth/refresh_token") {
                contentType(ContentType.Application.Json)
                setBody(DataPostRefreshToken("UM-BLOG-9999", "1104", "mechanic"))
            }.body()

        return images
    }

    suspend fun rejectRepair(offerId: String, orderId: String): ResponseResult {
        val response = httpClient
            .put("https://api-staging-v10.fleetify.id/api/orders/not_approve_order_any_level") {
                contentType(ContentType.Application.Json)
                setBody(
                    DataPostRejectRepairOrder(
                        loggedGAId = "GA-BLOG-2",
                        offerId = offerId,
                        orderId = orderId,
                        rejectionNote = "Tolak ya"
                    )
                )
            }.body<ResponseResult>()

        return response
    }

    suspend fun approveRepairOrder(offerId: String, orderId: String): ResponseResult {
        val response = httpClient
            .put("https://api-staging-v10.fleetify.id/api/orders/approve_level_1") {
                contentType(ContentType.Application.Json)
                setBody(
                    DataPostApproveRepairOrder(
                        loggedGAId = "GA-BLOG-2",
                        offerId = offerId,
                        orderId = orderId,
                    )
                )
            }.body<ResponseResult>()

        return response
    }


    suspend fun getRepairApprovalAdhoc(): List<RepairOrderModel> {
        val images = httpClient
            .get("https://api-staging-v10.fleetify.id/api/orders/waiting_approval_level_2?loggedGAId=GA-BLOG-3")
            .body<List<RepairOrderModel>>()
        return images
    }

    suspend fun getRepairDetailInfoAdhoc(): List<RepairDetailInfo> {
        val detailOrder = httpClient
            .get("https://api-staging-v10.fleetify.id/api/orders/order_info?orderId=ORD/23112022/215932/MBA/VHC-BLOG-2")
            .body<List<RepairDetailInfo>>()
        return detailOrder
    }

    suspend fun getRepairDetailAfterCheck(): List<RepairDetailAfterCheckItem> {
        val repairDetailAfterCheckItems = httpClient
            .get("https://api-staging-v10.fleetify.id/api/orders/check_result_photos?orderId=ORD/23112022/215932/MBA/VHC-BLOG-2")
            .body<List<RepairDetailAfterCheckItem>>()
        return repairDetailAfterCheckItems
    }

    suspend fun getRepairDetailPartList(): List<RepairDetailPartListItem> {
        val repairDetailPartList = httpClient
            .get("https://api-staging-v10.fleetify.id/api/orders/offer_detail_by_offer_id?offerId=SP-6/ORD/23112022/215932/MBA/VHC-BLOG-2")
            .body<List<RepairDetailPartListItem>>()
        return repairDetailPartList
    }

    suspend fun getRepairDetailPartTotalPrice(): RepairDetailPartTotalPrice {
        val repairDetailPartTotalPrice = httpClient
            .get("https://api-staging-v10.fleetify.id/api/orders/offer_price_by_offer_id?offerId=SP-6/ORD/23112022/215932/MBA/VHC-BLOG-2")
            .body<List<RepairDetailPartTotalPrice>>()
        return repairDetailPartTotalPrice[0]
    }

    suspend fun getRepairDetailWorkshopOfferNote(): String {
        val workshopOfferNote = httpClient
            .get("https://api-staging-v10.fleetify.id/api/orders/offer_letter_note_from_workshop?orderId=ORD/23112022/215932/MBA/VHC-BLOG-2")
            .body<String>()
        return workshopOfferNote
    }
}