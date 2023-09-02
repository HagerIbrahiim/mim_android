package com.trianglz.mimar.modules.services.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.mimar.modules.branches.data.remote.model.BranchDataModel
import com.trianglz.mimar.modules.currency.data.model.CurrencyDataModel

@Keep
data class ServiceDataModel(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "fees_from")
    val feesFrom: Double? = null,
    @Json(name = "fees_to")
    val feesTo: Double? = null,
    @Json(name = "duration_mins")
    val durationMins: Int? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "offered_location")
    val offeredLocation: String? = null,
    @Json(name = "is_active")
    val isActive: Boolean? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "is_in_cart")
    val isInCart: Boolean? = null,
    @Json(name = "currency")
    val currency: CurrencyDataModel? = null,
    @Json(name = "branch")
    val branch: BranchDataModel? = null,
    @Json(name = "branch_specialty_id")
    val branchSpecialtyId: Int? = null,
)
