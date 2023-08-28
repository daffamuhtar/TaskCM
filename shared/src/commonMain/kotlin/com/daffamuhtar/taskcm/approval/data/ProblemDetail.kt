package com.daffamuhtar.taskcm.approval.data

import kotlinx.serialization.Serializable

@Serializable
data class ProblemDetail(
    val driverName: String?,
    val issueId: String,
    val problemDate: String,
    val problemDetail: String,
    val problemId: String,
    val problemSource: String,
    val verificatorName: String?
)