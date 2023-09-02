package com.trianglz.mimar.modules.appointment_details.presentation.composables.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.HorizontalDivider


@Composable
fun AppointmentPaymentSummary(
    currency:  String?,
    totalPrice: () -> Double?,
    totalPriceWithoutVat: () -> Double?,
    vat: () -> Double?,
    vatAmount: () -> Double?,
    ) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
            .shadow(
                elevation = 0.5.dp,
                shape = MaterialTheme.shapes.small,
                spotColor = MaterialTheme.colors.primary.copy(alpha = .5F)
            )
            .background(MaterialTheme.colors.surface)
            .padding(all = MaterialTheme.dimens.screenGuideDefault)
            .clip(MaterialTheme.shapes.small)
    ) {

        AppointmentPriceItem({ StringWrapper(R.string.sub_total) },
            totalPriceWithoutVat,currency
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsLarge))

        AppointmentPriceItem(
            {
                StringWrapper { context ->
                    return@StringWrapper context.getString(
                        R.string.vat_value,
                        vat().toString()
                    )
                }
            },
            vatAmount , currency
        )

        HorizontalDivider(
            padding = PaddingValues(
                start = MaterialTheme.dimens.screenGuideDefault,
                end = MaterialTheme.dimens.screenGuideDefault,
                top = MaterialTheme.dimens.spaceBetweenItemsLarge,
                bottom =  MaterialTheme.dimens.spaceBetweenItemsLarge))

        AppointmentPriceItem({ StringWrapper(R.string.total_amount) },
            totalPrice,currency
        )

    }
}
