package com.daffamuhtar.taskcm.contacts.presentation.bird.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BirdImage(
    val problemId: String,
    @SerialName ("problem")
    val problemStage: String,
    val vehicleBrand: String
)