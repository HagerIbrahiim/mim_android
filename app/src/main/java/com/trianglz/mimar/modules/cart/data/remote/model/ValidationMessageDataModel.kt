package com.trianglz.mimar.modules.cart.data.remote.model


import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ValidationMessageDataModel(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "message")
    val message: String? = null,
    @Json(name = "required_action")
    val requiredAction: String? = null
)