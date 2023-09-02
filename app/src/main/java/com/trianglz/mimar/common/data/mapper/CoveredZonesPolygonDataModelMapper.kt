package com.trianglz.mimar.common.data.mapper

import com.trianglz.mimar.common.data.model.CoveredZonesPolygonDataModel
import com.trianglz.mimar.common.domain.model.CoveredZonesPolygonDomainModel

fun CoveredZonesPolygonDataModel.toDomain() =
    CoveredZonesPolygonDomainModel(
        lat ?:"", lng?:""
    )