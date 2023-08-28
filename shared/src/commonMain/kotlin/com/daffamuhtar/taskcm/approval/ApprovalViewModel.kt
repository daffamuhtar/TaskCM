package com.daffamuhtar.taskcm.approval

import com.daffamuhtar.taskcm.approval.data.DataPostRefreshToken
import com.daffamuhtar.taskcm.approval.data.RepairDetailAfterCheckItem
import com.daffamuhtar.taskcm.approval.data.RepairDetailInfo
import com.daffamuhtar.taskcm.approval.data.RepairItem
import com.daffamuhtar.taskcm.approval.data.response.ResponseRefreshToken
import com.daffamuhtar.taskcm.approval.utils.RepairListEvent
import com.daffamuhtar.taskcm.approval.utils.RepairListState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
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
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

data class ApprovalUiState(
    val images: List<RepairItem> = emptyList(),
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

            else -> Unit
        }
    }


    val uiState = _uiState.asStateFlow()
    var token =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjIiLCJ1c2VybmFtZSI6IlVNLUJMT0ctOTk5OSIsImlhdCI6MTY5MzE5MjA2MSwiZXhwIjoxNjkzMjc4NDYxfQ.9DwznJP4DgaXHh2FtI4fAXnhidTwX_I4kzZKiel2QrI"

    private val httpClient = HttpClient {

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
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

        defaultRequest {
            header("Content-Type", "application/json; charset=utf-8")
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

    suspend fun refreshToken(): ResponseRefreshToken? {
        return try {

            httpClient.post("https://api-staging-v10.fleetify.id/api/api/auth/refresh_token") {
                contentType(ContentType.Application.Json)
                setBody(DataPostRefreshToken("UM-BLOG-9999","1104","Mechanic"))
            }.body<ResponseRefreshToken>()

        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            null
        } catch (ex: ServerResponseException) {
            // 5xx - response
            println("Error: ${ex.response.status.description}")
            null
        }
    }


    private suspend fun getRepairApprovalAdhoc(): List<RepairItem> {
        val images = httpClient
            .get("https://api-staging-v10.fleetify.id/api/orders/waiting_approval_level_2?loggedGAId=GA-BLOG-3")
            .body<List<RepairItem>>()
        return images
    }

    suspend fun getRepairDetailInfoAdhoc(): List<RepairDetailInfo> {
        val detailOrder = httpClient
            .get("https://api-staging-v10.fleetify.id/api/orders/order_info?orderId=ORD/23112022/215932/MBA/VHC-BLOG-2")
            .body<List<RepairDetailInfo>>()
        return detailOrder
    }

    suspend fun getRepairDetailAfterCheck(): List<RepairDetailAfterCheckItem> {
        val RepairDetailAfterCheckItems = httpClient
            .get("https://api-staging-v10.fleetify.id/api/orders/check_result_photos?orderId=ORD/23112022/215932/MBA/VHC-BLOG-2")
            .body<List<RepairDetailAfterCheckItem>>()
        return RepairDetailAfterCheckItems
    }

    suspend fun getRepairDetailPartList(): List<RepairDetailAfterCheckItem> {
        val RepairDetailAfterCheckItems = httpClient
            .get("https://api-staging-v10.fleetify.id/api/orders/check_result_photos?orderId=ORD/23112022/215932/MBA/VHC-BLOG-2")
            .body<List<RepairDetailAfterCheckItem>>()
        return RepairDetailAfterCheckItems
    }
}