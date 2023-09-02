package com.trianglz.mimar.modules.user.data.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.user.data.model.UserDataModel

@Keep
data class UserResponseModel(
    @Json(name = "customer") val customer: UserDataModel,
) : SuccessMessageResponse()
