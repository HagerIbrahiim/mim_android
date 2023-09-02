package com.trianglz.mimar.modules.addresses.data.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.addresses.data.model.CustomerAddressDataModel

@Keep
data class AddressResponseModel (
    @Json(name = "customer_address")
    val customerAddress: CustomerAddressDataModel
): SuccessMessageResponse()