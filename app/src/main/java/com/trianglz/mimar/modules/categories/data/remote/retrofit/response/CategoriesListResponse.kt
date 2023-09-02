package com.trianglz.mimar.modules.categories.data.remote.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.categories.data.remote.model.CategoryDataModel

@Keep
class CategoriesListResponse(
    @Json(name = "categories")
    val categories: List<CategoryDataModel>?
) : SuccessMessageResponse()