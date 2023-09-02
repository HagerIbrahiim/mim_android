package com.trianglz.mimar.modules.cart.domain.model


data class UpdateCartDomainModel(
    val customerAddressId: Int?=null,
    val notes: String? = null,
    val appointmentLocation: String? = null,
    val paymentMethod: String? = null,
    val firstServiceTime: String? = null,
    val appointmentDate: String? = null,
)
