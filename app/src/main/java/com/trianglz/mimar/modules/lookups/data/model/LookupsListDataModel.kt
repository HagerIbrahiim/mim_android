package com.trianglz.mimar.modules.lookups.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class LookupsListDataModel (
	@Json(name = "specialties")
		val specialties: List<LookupsDataModel>?= null,
	@Json(name = "genders")
		val genders: List<LookupsDataModel>?= null,
	@Json(name = "offered_locations")
		val offeredLocations: List<LookupsDataModel>?= null,
)