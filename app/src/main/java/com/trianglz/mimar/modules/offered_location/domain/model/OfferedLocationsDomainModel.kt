package com.trianglz.mimar.modules.offered_location.domain.model

data class OfferedLocationsDomainModel(
    val id: Int,
    val name: String?= null,
    val value: String?= null,
    val isChecked: Boolean = false,
    )