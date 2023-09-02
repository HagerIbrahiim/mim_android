package com.trianglz.mimar.common.presentation.mapper

import com.trianglz.mimar.common.domain.model.CoveredZonesDomainModel
import com.trianglz.mimar.common.presentation.models.CoveredZonesUIModel


fun CoveredZonesDomainModel.toUI()=
    CoveredZonesUIModel(id,name,polygon?.map { it.toUI() })