package com.trianglz.mimar.modules.cart.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.selectedItemBackground
import com.trianglz.mimar.modules.addresses.ui.composables.AddressItem
import com.trianglz.mimar.modules.cart.presentation.composables.cart_address.RequiredAddressCartItem
import com.trianglz.mimar.modules.cart.presentation.model.CartAddressSectionUIModel


@Composable
fun CartAddressSection(
    item: CartAddressSectionUIModel,
    onAddAddressClicked: () -> Unit,
    onChangeAddressClicked: () -> Unit
) {
    val selectedAddress by remember {
        item.selectedAddress
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
    ) {
        Text(
            text = stringResource(id = R.string.address),
            modifier = Modifier.padding(vertical = MaterialTheme.dimens.spaceBetweenItemsXLarge),
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle2.copy(
                fontWeight = FontWeight.W600
            )
        )
        if (selectedAddress != null) {
            AddressItem(
                address = { selectedAddress!! },
                startIcon = R.drawable.ic_location,
                showEditBtn = true,
                showDeleteBtn = false,
                itemBackgroundColor = { MaterialTheme.colors.selectedItemBackground.copy(alpha = 0.2f) },
                onEditAddressClicked = { onChangeAddressClicked.invoke() })
        } else {
            RequiredAddressCartItem(onAddAddressClicked)
        }
    }


}
