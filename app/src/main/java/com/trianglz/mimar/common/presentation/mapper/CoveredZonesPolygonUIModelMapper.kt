package com.trianglz.mimar.common.presentation.mapper

import com.trianglz.mimar.common.domain.model.CoveredZonesPolygonDomainModel
import com.trianglz.mimar.common.presentation.models.CoveredZonesPolygonUIModel

fun CoveredZonesPolygonDomainModel.toUI() =
    CoveredZonesPolygonUIModel(
        lat, lng
    )