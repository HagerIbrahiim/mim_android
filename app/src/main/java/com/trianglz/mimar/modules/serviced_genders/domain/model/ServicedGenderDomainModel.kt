package com.trianglz.mimar.modules.serviced_genders.domain.model


data class ServicedGenderDomainModel(
    val id: Int,
    val name: String?= null,
    val value: String,
    val isChecked: Boolean = false,
    )
