package com.trianglz.mimar.modules.payment.data.remote.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.payment.data.remote.model.CheckoutIdDataModel

@Keep
data class CheckoutIdResponse (
    @Json(name = "checkout_id")
    var checkoutId: CheckoutIdDataModel?,
): SuccessMessageResponse()