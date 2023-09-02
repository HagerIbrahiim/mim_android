package com.trianglz.mimar.common.data.mapper

import com.trianglz.mimar.common.data.model.CoveredZonesDataModel
import com.trianglz.mimar.common.domain.model.CoveredZonesDomainModel


fun CoveredZonesDataModel.toDomain()=
    CoveredZonesDomainModel(id,name ?: "",polygon?.map { it.toDomain() })