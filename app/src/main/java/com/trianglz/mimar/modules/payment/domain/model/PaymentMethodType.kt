package com.trianglz.mimar.modules.payment.domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.payment.presentation.model.PaymentMethodUIModel
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class PaymentMethodType constructor(open val id: Int, open val key: String, @StringRes open val displayName: Int?, @DrawableRes open val icon: Int, open val hyperPayKey:HashSet<String>?): Parcelable {
    @Parcelize object Cash: PaymentMethodType(id = 1, key = "cash", displayName = R.string.cash, icon = R.drawable.ic_cash, hyperPayKey = null)
    @Parcelize open class Online(override val id: Int = 2, override val key: String = "online", override val displayName: Int = R.string.online, override val icon: Int = R.drawable.ic_credit_card, override val hyperPayKey:HashSet<String>? = null): PaymentMethodType(id = id, key = key, displayName = displayName, icon = icon, hyperPayKey = hyperPayKey) {
        @Parcelize object CreditCard: Online(id = 3, key = "visa_master", displayName =  R.string.visa, icon = R.drawable.ic_credit_card, hyperPayKey = hashSetOf("VISA", "MASTER"))
        @Parcelize object Mada: Online(id = 4, key = "mada", displayName = R.string.mada, icon = R.drawable.ic_credit_card, hyperPayKey = hashSetOf("MADA"))
   }

}

fun String?.toPaymentMethodType(): PaymentMethodType {
    return when(this) {
        PaymentMethodType.Cash.key -> PaymentMethodType.Cash
        PaymentMethodType.Online.CreditCard.key-> PaymentMethodType.Online.CreditCard
        PaymentMethodType.Online.Mada.key-> PaymentMethodType.Online.Mada

        else -> PaymentMethodType.Online()
    }
}

fun getPaymentMethodType(): List<PaymentMethodType> {
    return listOf(PaymentMethodType.Cash, PaymentMethodType.Online())
}

fun String?.toOnlinePaymentMethodType(): PaymentMethodType.Online? {
    return when(this) {
        PaymentMethodType.Online.CreditCard.key -> PaymentMethodType.Online.CreditCard
        PaymentMethodType.Online.Mada.key -> PaymentMethodType.Online.Mada
        else -> null
    }
}


fun getOnlinePaymentMethodType(): List<PaymentMethodType.Online> {
    return listOf(PaymentMethodType.Online.CreditCard, PaymentMethodType.Online.Mada)
}