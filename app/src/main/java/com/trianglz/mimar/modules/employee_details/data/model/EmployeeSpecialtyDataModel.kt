package com.trianglz.mimar.modules.employee_details.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.trianglz.mimar.modules.services.data.model.ServiceDataModel
import com.trianglz.mimar.modules.specilaities.data.model.SpecialtiesDataModel

@Keep
data class EmployeeSpecialtyDataModel(
    @Json(name = "services")
    val services: List<ServiceDataModel>? = null,
    @Json(name = "specialty")
    val specialty: SpecialtiesDataModel? = null
)