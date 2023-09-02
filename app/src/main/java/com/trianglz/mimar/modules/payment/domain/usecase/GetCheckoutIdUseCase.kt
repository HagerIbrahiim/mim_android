package com.trianglz.mimar.modules.payment.domain.usecase

import com.trianglz.mimar.modules.payment.domain.model.CheckoutIdDomainModel
import com.trianglz.mimar.modules.payment.domain.repository.PaymentRepo
import javax.inject.Inject

class GetCheckoutIdUseCase @Inject constructor(private val paymentRepo: PaymentRepo) {
    suspend fun execute(orderId:Int, paymentOption:String): CheckoutIdDomainModel? {
        return paymentRepo.getCheckoutId(orderId, paymentOption)
    }
}