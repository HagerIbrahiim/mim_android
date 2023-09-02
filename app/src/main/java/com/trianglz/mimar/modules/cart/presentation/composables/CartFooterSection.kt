package com.trianglz.mimar.modules.cart.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.trianglz.core.presentation.enums.Locales
import com.trianglz.core.presentation.helper.MultipleEventsCutter
import com.trianglz.core.presentation.helper.get
import com.trianglz.core.presentation.utils.getAppLocale
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.cart.presentation.model.CartFooterSectionUIModel


@Composable
fun CartFooterSection(item: CartFooterSectionUIModel, onConfirmAppointmentClicked: () -> Unit) {

    val multipleEventsCutter = remember { MultipleEventsCutter.get() }

    val totalPrice by remember {
        item.totalEstimatedPrice
    }
    val totalTime by remember {
        item.totalEstimatedTime
    }
    val enableConfirmBtn by remember {
        item.enableConfirmBtn
    }

    val currency:String? = remember {
        if (getAppLocale() == Locales.ARABIC.code)
            item.currency.value?.name
        else
            item.currency.value?.symbol
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)) {
        Text(
            text = stringResource(id = R.string.total),
            modifier = Modifier.padding(top = MaterialTheme.dimens.spaceBetweenItemsXLarge, bottom = MaterialTheme.dimens.spaceBetweenItemsSmall),
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle2.copy(
                fontWeight = FontWeight.W600
            )
        )

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(
            MaterialTheme.dimens.spaceBetweenItemsXSmall)) {
            Text(
                text = stringResource(
                    id = R.string.price_with_currency,
                    totalPrice ?: 0.0,
                    currency ?: stringResource(id = R.string.saudi_riyal)
                ),
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            ImageFromRes(
                imageId = R.drawable.estimate_duration_icon,
                tintColor = MaterialTheme.colors.onBackground,
                modifier = Modifier
//                    .shimmer(service().showShimmer)
                    .padding(end = MaterialTheme.dimens.screenGuideDefault)
            )
            Spacer(modifier = Modifier.weight(1f))
            totalTime?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideXLarge))

        BaseComposeMainUIComponents.LocalMainComponent.AppButton(
            modifier = Modifier,
            text = R.string.confirm,
            enabled = enableConfirmBtn,
            textStyle = MaterialTheme.typography.button,
            isAddAlphaToDisabledButton = true,
            disabledColor = MaterialTheme.colors.primary,
            backgroundColor = MaterialTheme.colors.primary,
            onClick = {
                multipleEventsCutter.clickWithThrottle {
                    onConfirmAppointmentClicked()
                }

            }
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideXLarge))
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideXLarge))
    }
}
