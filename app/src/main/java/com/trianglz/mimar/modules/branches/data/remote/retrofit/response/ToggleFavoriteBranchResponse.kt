package com.trianglz.mimar.modules.branches.data.remote.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.branches.data.remote.model.BranchDataModel
import com.trianglz.mimar.modules.categories.data.remote.model.CategoryDataModel

@Keep
class ToggleFavoriteBranchResponse(
    @Json(name = "branch")
    val branch: BranchDataModel?
) : SuccessMessageResponse()