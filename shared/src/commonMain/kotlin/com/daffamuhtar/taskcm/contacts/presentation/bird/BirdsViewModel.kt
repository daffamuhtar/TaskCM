package com.daffamuhtar.taskcm.contacts.presentation.bird

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.daffamuhtar.taskcm.contacts.presentation.bird.model.BirdImage
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import kotlinx.serialization.json.Json

data class BirdsUiState(
    val images: List<BirdImage> = emptyList(),
    val selectedCategory: String? = null
) {
    val categories = images.map { it.problemStage }.toSet()
    val selectedImages = images.filter { it.problemStage == selectedCategory }
}

class BirdsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<BirdsUiState>(BirdsUiState())
    val uiState = _uiState.asStateFlow()
    var token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjIiLCJ1c2VybmFtZSI6IlVNLUJMT0ctOTk5OSIsImlhdCI6MTY5Mjg0MzUwMiwiZXhwIjoxNjkyOTI5OTAyfQ.LF_GD6uOzavFcGlUZKxfn8tnPFC5HOGKTVp9y2rcQDQ"

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
                    BearerTokens(token, token)
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

    fun updateImages() {
        viewModelScope.launch {
            val images = getImages()
            _uiState.update {
                it.copy(images = images)
            }
        }
    }

    private suspend fun getImages(): List<BirdImage> {
        val images = httpClient
            .get("https://api-staging-v10.fleetify.id/api/UM/report_history_v2?loggedUMId=UM-BLOG-9999&issueType=vehicle&problemStageId=0")
            .body<List<BirdImage>>()
        return images
    }
}