package com.trianglz.mimar.modules.payment.presentation.model

import com.oppwa.mobile.connect.payment.BillingAddress
import com.trianglz.mimar.modules.payment.domain.model.PaymentMethodType

data class PaymentCheckoutInfoUIModel(val checkoutId:String, val billingInfo :BillingAddress?, val paymentMethod: PaymentMethodType)