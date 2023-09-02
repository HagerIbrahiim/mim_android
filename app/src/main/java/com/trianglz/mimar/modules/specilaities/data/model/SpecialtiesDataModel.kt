package com.trianglz.mimar.modules.specilaities.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class SpecialtiesDataModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String?= null,
    var isChecked : Boolean = false,
)

