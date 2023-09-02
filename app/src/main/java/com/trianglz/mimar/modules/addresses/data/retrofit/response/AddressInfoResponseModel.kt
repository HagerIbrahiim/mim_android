package com.trianglz.mimar.modules.addresses.data.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.mimar.modules.addresses.data.model.AddressInfoDataModel

@Keep
class AddressInfoResponseModel(
    @Json(name = "data")
    val addressData: AddressInfoDataModel
)