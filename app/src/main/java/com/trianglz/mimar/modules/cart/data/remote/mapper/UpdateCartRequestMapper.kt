package com.trianglz.mimar.modules.cart.data.remote.mapper

import com.trianglz.mimar.modules.cart.data.remote.retrofit.request.UpdateCartRequest
import com.trianglz.mimar.modules.cart.domain.model.UpdateCartDomainModel

fun UpdateCartDomainModel.toRequestModel(): UpdateCartRequest = UpdateCartRequest(
    customerAddressId = customerAddressId,
    notes = notes,
    appointmentLocation = appointmentLocation,
    paymentMethod = paymentMethod,
    firstServiceTime = firstServiceTime,
    appointmentDate = appointmentDate

)