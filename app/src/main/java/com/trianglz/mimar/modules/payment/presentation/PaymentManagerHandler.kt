package com.trianglz.mimar.modules.payment.presentation

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.oppwa.mobile.connect.checkout.meta.CheckoutActivityResult
import com.oppwa.mobile.connect.checkout.meta.CheckoutActivityResultContract
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings
import com.oppwa.mobile.connect.provider.Connect
import com.oppwa.mobile.connect.provider.Transaction
import com.oppwa.mobile.connect.provider.TransactionType
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.payment.presentation.model.PaymentCheckoutInfoUIModel

class PaymentManagerHandler(
    private val registry: ActivityResultRegistry,
    private val context: Context,
    private val block: (checkoutId: String) -> Unit,
    private val onDone: () -> Unit,
    private val onError: (String)-> Unit,
) : DefaultLifecycleObserver {

    private var checkoutLauncher: ActivityResultLauncher<CheckoutSettings>? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        checkoutLauncher = registry.register("payment", CheckoutActivityResultContract()) { result: CheckoutActivityResult ->
            handleCheckoutResult(result)
        }
    }

    fun handleOpenCheckoutActivity(checkoutInfo: PaymentCheckoutInfoUIModel) {
        val paymentBrands =checkoutInfo.paymentMethod.hyperPayKey
        val checkoutSettings = CheckoutSettings(checkoutInfo.checkoutId, paymentBrands, Connect.ProviderMode.TEST)
        checkoutSettings.shopperResultUrl =  context.getString(R.string.mimar_payment_callback_scheme) + "://result"
//        checkoutSettings.billingAddress =checkoutInfo.billingInfo
        checkoutSettings.isTotalAmountRequired = true
        checkoutLauncher?.launch(checkoutSettings)
    }

    private fun handleCheckoutResult(result: CheckoutActivityResult) {
        if (result.isCanceled) {
            onDone.invoke()
            // shopper cancelled the checkout process
            return
        }

        if (result.isErrored) {
            result.paymentError?.errorMessage?.let { onError(it) }
            // error occurred during the checkout process
            return
        }

        val transaction: Transaction? = result.transaction
        val resourcePath = result.resourcePath

        if (transaction != null) {
            if (transaction.transactionType === TransactionType.SYNC) {
                val checkoutId=result.transaction?.paymentParams?.checkoutId
                // request payment status
                checkoutId?.let {
                    block.invoke(it)
                }
            } else {
                // wait for the asynchronous transaction shopper  callback in the onNewIntent()
            }
        }
    }


}