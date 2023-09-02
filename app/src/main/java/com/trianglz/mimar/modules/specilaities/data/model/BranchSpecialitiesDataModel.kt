package com.trianglz.mimar.modules.specilaities.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class  BranchSpecialitiesDataModel(
    @Json(name = "value")
    val id: String,
    @Json(name = "specialty_title")
    val title: String?= null,
    var isChecked : Boolean = false,
)

