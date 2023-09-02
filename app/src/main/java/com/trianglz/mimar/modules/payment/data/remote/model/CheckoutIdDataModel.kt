package com.trianglz.mimar.modules.payment.data.remote.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class CheckoutIdDataModel(
    @Json(name = "checkout_id")
    var checkoutId: String?,
)
