package com.trianglz.mimar.common.domain.model


data class CoveredZonesDomainModel (
    val id: Int,
    val name: String,
    val polygon: List<CoveredZonesPolygonDomainModel>?
)