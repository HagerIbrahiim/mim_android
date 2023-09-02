package com.trianglz.mimar.modules.categories.data.remote.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

/**
 * Created by hassankhamis on 04,January,2023
 */

@Keep
data class CategoryDataModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "image")
    val image: String?,
    @Json(name = "title")
    val title: String?,
)
