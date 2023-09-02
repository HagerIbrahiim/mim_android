package com.trianglz.mimar.modules.addresses.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.TextStartsWithIcon
import com.trianglz.mimar.common.presentation.ui.theme.addressCheckedItemColor
import com.trianglz.mimar.common.presentation.ui.theme.addressUnCheckedItemColor
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeMedium
import com.trianglz.mimar.common.presentation.ui.theme.xxSmall
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel

@Composable
fun AddressItem(
    address: () -> CustomerAddressUIModel,
    startIcon:  Int?= null,
    showEditBtn: Boolean = true,
    showDeleteBtn: Boolean = true,
    filterByBranchIdInCart: () -> Boolean? = { null},
    itemBackgroundColor:  (@Composable () -> Color)?= null,
    addressItemClicked: ((CustomerAddressUIModel) -> Unit?)? = null,
    onEditAddressClicked: (CustomerAddressUIModel) -> Unit = {},
    onDeleteAddressClicked: (Int) -> Unit = {}
) {



    val checkboxIcon = remember(address().isDefault, address().isChecked) {
        startIcon?.let {
            startIcon
        } ?: run {
            if (address().isChecked) R.drawable.radio_button_selected else R.drawable.radio_button_unselected
        }
    }

    val isShowDeleteBtn = remember(address().isDefault) {
        address().isDefault == false && showDeleteBtn
    }


    val showAddressDesc = remember(address().city) {
        address().city != null
    }

    val isBranchNotSupported = remember(address().isSupported) {
        !address().isSupported && filterByBranchIdInCart() == true
    }

    val enableItemClickAction = remember( address().showShimmer , address().isSupported) {
        !address().showShimmer && addressItemClicked != null
                && !isBranchNotSupported
    }



    val checkedItemBackgroundColor: @Composable () -> Color = remember(address().isChecked) {
        {
            MaterialTheme.colors.addressCheckedItemColor.copy(.2F)
        }
    }

    val addressBackgroundColor: @Composable () -> Color =
        remember(address().isDefault, address().isChecked) {
            {
                itemBackgroundColor?.let {
                    it()
                } ?: run {

                    if (address().isChecked) checkedItemBackgroundColor()
                    else
                        MaterialTheme.colors.addressUnCheckedItemColor.copy(.2F)
                }
            }
        }


    val unSupportedLocationAlpha = remember(isBranchNotSupported) {
        if (isBranchNotSupported) 0.3f else 1f
    }

    val innerPaddingXSmall : @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.innerPaddingXSmall / 2
        }
    }

    val smallPadding: @Composable () -> Dp = remember {
        { MaterialTheme.dimens.innerPaddingSmall }
    }

    val contentPaddingStart: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.iconSizeMedium + smallPadding()
        }
    }


    val context = LocalContext.current

    val endPadding : @Composable () -> Dp = remember {
        {
            if (showEditBtn) MaterialTheme.dimens.screenGuideDefault - innerPaddingXSmall()
            else MaterialTheme.dimens.screenGuideDefault
        }
    }

    val unSupportedMessageTopPadding : @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.spaceBetweenItemsMedium + 2.dp
        }
    }

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.xxSmall)
            .background(addressBackgroundColor())
            .clickable(enabled = enableItemClickAction,
                onClick = { addressItemClicked?.invoke(address()) })
            .padding(vertical = MaterialTheme.dimens.screenGuideDefault)
            .padding(start = MaterialTheme.dimens.screenGuideDefault, end = endPadding())
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                //.then(unSupportedAddressModifier())
        ) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(MaterialTheme.dimens.iconSizeMedium)
                        .alpha(unSupportedLocationAlpha)
                        .shimmer(address().showShimmer),
                ) {

                    Image(
                        imageVector = ImageVector.vectorResource(id = checkboxIcon),
                        contentDescription = ""
                    )
                }

                Text(
                    text = address().title?.getString(context) ?: "",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.W700
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(horizontal = smallPadding())
                        .alpha(unSupportedLocationAlpha)
                        .shimmer(
                            address().showShimmer,
                            shimmerWidth = 0.5f,
                        )
                        .weight(1F)
                )

                if (isShowDeleteBtn) {

                    IconButton(
                        onClick = { onDeleteAddressClicked(address().id) },
                        enabled = !address().isChecked,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(30.dp)
                            .shimmer(address().showShimmer),
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.delete_icon),
                            contentDescription = "",
                            modifier = Modifier.padding(innerPaddingXSmall())

                        )
                    }

                    Divider(
                        color = MaterialTheme.colors.primary.copy(alpha = .3F),
                        modifier = Modifier
                            .padding(horizontal = innerPaddingXSmall())
                            .width(MaterialTheme.dimens.borderMedium)
                            .fillMaxHeight()
                            .padding(vertical = MaterialTheme.dimens.innerPaddingXSmall)

                    )
                }

                if (showEditBtn) {

                    IconButton(
                        onClick = { onEditAddressClicked(address()) },
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(30.dp)
                            .shimmer(address().showShimmer),
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
                            contentDescription = "",
                            modifier = Modifier.padding(innerPaddingXSmall())
                        )
                    }
                }
            }


            if (showAddressDesc) {
                Spacer(modifier  = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXSmall))
                Text(
                    text = "${address().buildingNumber} ${address().streetName} ${address().district} ${address().city}, ${address().country} ",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.W400
                    ),
                    modifier = Modifier
                        .padding(start = contentPaddingStart())
                        .alpha(unSupportedLocationAlpha)
                        .shimmer(
                            address().showShimmer,
                            shimmerWidth = .8f,
                        )

                )
            }

        }

        if (isBranchNotSupported) {

            Spacer(modifier  = Modifier.height(unSupportedMessageTopPadding()))

            TextStartsWithIcon(
                drawableRes = R.drawable.ic_error,
                textStyle = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.error,
                    fontWeight = FontWeight.W400
                ),
                data = stringResource(id = R.string.unsupported_location),
                modifier = Modifier
                    .padding(start = contentPaddingStart())
                    .shimmer(
                        address().showShimmer,
                    )
            )

        }
    }


}
