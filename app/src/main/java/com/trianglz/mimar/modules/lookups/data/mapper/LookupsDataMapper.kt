package com.trianglz.mimar.modules.lookups.data.mapper

import com.trianglz.mimar.modules.lookups.data.model.LookupsDataModel
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationsDomainModel
import com.trianglz.mimar.modules.serviced_genders.domain.model.ServicedGenderDomainModel

fun LookupsDataModel.toOfferedLocationDomain(id: Int) = OfferedLocationsDomainModel(
    id, name, value = value,
)

fun OfferedLocationsDomainModel.toData() = LookupsDataModel(
    name = name ?:"", value =value ?:"",
)

fun ServicedGenderDomainModel.toData() = LookupsDataModel(
     name = name ?:"", value =value ,
)

fun LookupsDataModel.toGenderDomainModel(id: Int) = ServicedGenderDomainModel(
    id = id, name = name,value ?:"",
)