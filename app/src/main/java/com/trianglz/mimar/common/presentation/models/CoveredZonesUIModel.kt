package com.trianglz.mimar.common.presentation.models



data class CoveredZonesUIModel (
    val id: Int,
    val name: String,
    val polygon: List<CoveredZonesPolygonUIModel>?
)