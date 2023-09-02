package com.trianglz.mimar.modules.branches.data.remote.model

import com.squareup.moshi.Json

data class BranchReviewDataModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "rating")
    val rating: Int,
    @Json(name = "feedback")
    val feedback: String? = null,
    @Json(name = "customer_name")
    val customerName: String? = null,
    @Json(name = "created_date")
    val createdDate: String? = null


)
