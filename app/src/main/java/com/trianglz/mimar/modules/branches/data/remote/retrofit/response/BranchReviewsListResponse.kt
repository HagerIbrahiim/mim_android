package com.trianglz.mimar.modules.branches.data.remote.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.branches.data.remote.model.BranchReviewDataModel

@Keep
class BranchReviewsListResponse(
    @Json(name = "branch_reviews")
    val reviews: List<BranchReviewDataModel>?
) : SuccessMessageResponse()