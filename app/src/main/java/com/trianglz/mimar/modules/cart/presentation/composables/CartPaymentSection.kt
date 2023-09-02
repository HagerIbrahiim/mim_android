package com.trianglz.mimar.modules.cart.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.trianglz.core_compose.presentation.composables.CardWithRoundedBorder
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.common.presentation.compose_views.RadioButtonWithTextItem
import com.trianglz.mimar.common.presentation.ui.theme.dividerColor
import com.trianglz.mimar.modules.cart.presentation.model.CartPaymentTypesSectionUIModel


@Composable
fun CartPaymentSection(item: CartPaymentTypesSectionUIModel, onPaymentChanged: (Int) -> Unit) {
    CardWithRoundedBorder(modifier = Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideDefault), borderColor = MaterialTheme.colors.surface, backgroundColor = MaterialTheme.colors.surface) {
        Column(
            modifier = Modifier
        ) {
            item.paymentMethods.forEachIndexed { index, paymentItem ->
                paymentItem?.let {
                    RadioButtonWithTextItem(
                        item = paymentItem,
                        onCheckedChanged = {
                            onPaymentChanged.invoke(it.uniqueId)
                        }
                    )
                    if (index + 1 != item.paymentMethods.size)
                        Divider(
                            color = MaterialTheme.colors.dividerColor,
                            thickness = MaterialTheme.dimens.borderMedium,
                            modifier = Modifier.padding(
                                horizontal = MaterialTheme.dimens.screenGuideDefault
                            )
                        )
                }

            }
        }
    }
}