package com.trianglz.mimar.modules.branches.data.remote.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.mimar.common.data.model.CoveredZonesDataModel
import com.trianglz.mimar.common.data.model.WorkingHoursDataModel
import com.trianglz.mimar.modules.categories.data.remote.model.CategoryDataModel

@Keep
data class BranchDetailsDataModel(
    @property:Json(name = "phone_number")
    val phoneNumber: String? = null,
    @property:Json(name = "serviced_gender")
    val servicedGender: String? = null,
    @property:Json(name = "service_provider_id")
    val serviceProviderId: Int? = null,
    @property:Json(name = "category_id")
    val categoryId: Int? = null,
    @property:Json(name = "city")
    val city: String? = null,
    @property:Json(name = "building_number")
    val buildingNumber: String? = null,
    @property:Json(name = "street")
    val street: String? = null,
    @property:Json(name = "lat")
    val lat: Double? = null,
    @property:Json(name = "lng")
    val lng: Double? = null,
    @property:Json(name = "category")
    val category: CategoryDataModel? = null,
    @property:Json(name = "working_hours")
    val workingHours: List<WorkingHoursDataModel>? = null,
    @property:Json(name = "covered_zones")
    val coveredZones: List<CoveredZonesDataModel>? = null

) : BranchDataModel()