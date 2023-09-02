package com.trianglz.mimar.modules.branches.data.remote.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

/**
 * Created by hassankhamis on 05,January,2023
 */

@Keep
open class BranchDataModel(
    @property:Json(name = "id")
    open var  id: Int = -1,
    @property:Json(name = "image")
    open var  image: String? = null,
    @property:Json(name = "name")
    open var  name: String? = "",
    @property:Json(name = "address")
    open var  location: String? = "",
    @property:Json(name = "branch_reviews_count")
    open var  reviewsCount: Int? = null,
    @property:Json(name = "rating")
    open var  rating: Float? = null,
    @property:Json(name = "is_favored")
    open var  isFavorite: Boolean? = false,
    @property:Json(name = "offered_location")
    open var  offeredLocation: String?= "",
    @property:Json(name = "service_provider_logo")
    open var serviceProviderLogo: String? = null,
    @property:Json(name = "off_days")
    open var offDays: List<String>? = null,
    @property:Json(name = "payment_methods")
    open var paymentMethods: String? = null,
    @property:Json(name = "status")
    open var status: String? = null,
)

