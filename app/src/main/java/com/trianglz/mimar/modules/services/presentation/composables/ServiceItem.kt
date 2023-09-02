package com.trianglz.mimar.modules.services.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import com.trianglz.core.presentation.enums.Locales
import com.trianglz.core.presentation.helper.MultipleEventsCutter
import com.trianglz.core.presentation.helper.get
import com.trianglz.core.presentation.utils.getAppLocale
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.HorizontalDivider
import com.trianglz.mimar.common.presentation.compose_views.ImageItem
import com.trianglz.mimar.common.presentation.compose_views.TextStartsWithIcon
import com.trianglz.mimar.common.presentation.ui.theme.Sycamore
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeMedium
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeSmall
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeXLarge
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeXXSmall
import com.trianglz.mimar.common.presentation.ui.theme.innerPaddingXXSmall
import com.trianglz.mimar.common.presentation.ui.theme.innerPaddingXXXSmall
import com.trianglz.mimar.common.presentation.ui.theme.personImageSizeSmall
import com.trianglz.mimar.common.presentation.ui.theme.xxSmall
import com.trianglz.mimar.common.presentation.ui.theme.xxxSmall
import com.trianglz.mimar.modules.cart.presentation.model.CartValidationActionType
import com.trianglz.mimar.modules.services.domain.model.ServiceStatus
import com.trianglz.mimar.modules.services.presentation.model.ServiceType
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun ServiceItem(
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = MaterialTheme.dimens.innerPaddingLarge,
    service: () -> ServiceUIModel,
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }

    val addedInCart by remember {
        service().isAdded
    }

    val employee = remember(service().assignedEmployee) {
        service().assignedEmployee
    }

    val addedServiceIcon = remember(addedInCart) {
        if (addedInCart) R.drawable.ic_check_icon else R.drawable.add_icon
    }

    val addIconWidth = remember {
        30.dp
    }

    val currency = remember {
        if (getAppLocale() == Locales.ARABIC.code)
            service().currency?.name
        else
            service().currency?.symbol
    }

    val fees: @Composable () -> String =
        remember(service().feesFrom, service().feesTo, service().hasExact) {
            {
                service().getFees()
            }
        }


    val min: @Composable () -> String = remember(service().durationMins, service().startAt, service().status) {
        {
            if (service().startAt.isNullOrEmpty() || service().status == ServiceStatus.Unscheduled) {
                "${service().durationMins} ${
                    pluralStringResource(
                        id = R.plurals.no_of_min,
                        service().durationMins ?: 0
                    )
                }"
            } else {
                "${service().startAt} - ${service().endAt}"
            }
        }
    }


    val addedServiceBackground: @Composable () -> Brush = remember(addedInCart) {
        {
            if (addedInCart)
                Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colors.onPrimary,
                        MaterialTheme.colors.onPrimary,
                    )
                )
            else
                Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colors.primary,
                        Sycamore,
                        MaterialTheme.colors.primary,
                    )
                )
        }
    }

    val branchNameVisibility = remember(service().type) {
        if (service().type is ServiceType.SearchService)
            Visibility.Visible else Visibility.Gone
    }

    val defaultGuide = MaterialTheme.dimens.screenGuideDefault

    val deleteBtnVisibility = remember {
        if (service().type == ServiceType.CartService) Visibility.Visible else Visibility.Gone
    }

    val validationServiceAlertVisibility = remember(service().hasServiceValidation) {
        if (service().type == ServiceType.CartService && service().hasServiceValidation) Visibility.Visible else Visibility.Gone
    }

    val changeBtnVisibility = remember {
        if (service().type == ServiceType.CartService) Visibility.Visible else Visibility.Gone
    }
    val validationEmployeeAlertVisibility = remember(service().hasEmployeeValidation) {
        if (service().hasEmployeeValidation) Visibility.Visible else Visibility.Gone
    }

    val deleteSelectedEmployeeVisibility = remember(service().hasEmployeeValidation, service().assignedEmployee?.isAnyone) {
        if (service().assignedEmployee?.isAnyone == false) Visibility.Visible else Visibility.Gone
    }
    val employeeConflictAlpha = remember(service().hasEmployeeValidation) {
        if (service().hasEmployeeValidation) 0.5f else 1f
    }

    val approximateTimeVisibility = remember(service().startAt, service().status) {
        if (service().startAt.isNullOrEmpty() || service().status == ServiceStatus.Unscheduled) Visibility.Visible else Visibility.Gone
    }
    val descriptionVisibility = remember(service().description) {
        if (service().description.isNullOrEmpty()) Visibility.Gone else Visibility.Visible
    }

    val statusVisibility = remember(service().appointmentStatusType ,service().status,) {
        if (service().showStatusRow) Visibility.Visible else Visibility.Invisible
    }

    val reportIconVisibility = remember(service().appointmentStatusType, service().status) {
        if (service().showReportIcon) Visibility.Visible else Visibility.Gone
    }

    val reportBtnInnerPadding: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.innerPaddingXXSmall
        }
    }

    val innerPaddingLarge: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.innerPaddingLarge
        }
    }

    val containerBottomPadding: @Composable () -> Dp =
        remember(service().showStatusRow,service().showReportIcon) {
            {
                if (service().showReportIcon) innerPaddingLarge() - reportBtnInnerPadding()
                else innerPaddingLarge()
            }
        }

    val statusIconInnerPadding : @Composable () -> Modifier = remember {
        {
            if (service().status is ServiceStatus.Reported)
                Modifier.padding(
                    horizontal = MaterialTheme.dimens.innerPaddingXXXSmall,
                    vertical = MaterialTheme.dimens.innerPaddingXXXSmall / 2
                )
            else
                Modifier.padding(MaterialTheme.dimens.innerPaddingXXXSmall)
        }
    }

    val employeeName : @Composable () -> String = remember(employee?.isAnyone){
        {
            if (employee?.isAnyone == true) stringResource(id = R.string.anyone) else employee?.userName ?:""
        }
    }

    Column(modifier = Modifier) {
        if (service().waitingTime != null) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
                    .clip(MaterialTheme.shapes.xxSmall)
                    .background(MaterialTheme.colors.secondary.copy(alpha = .2F))
                    .padding(
                        horizontal = MaterialTheme.dimens.innerPaddingLarge,
                        vertical = MaterialTheme.dimens.innerPaddingSmall
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImageFromRes(
                    imageId = R.drawable.ic_waiting,
                    modifier = Modifier,
                    tintColor = MaterialTheme.colors.primary
                )

                Spacer(modifier = Modifier.width(MaterialTheme.dimens.spaceBetweenItemsMedium))

                Text(
                    text = stringResource(id = R.string.waiting_time_is_mins, service().waitingTime ?: ""),
                    style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primary)
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsDefault))
        }
        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = MaterialTheme.shapes.small
                )
                .clip(shape = MaterialTheme.shapes.small)
                .clickWithThrottle(enabled = service().onServiceItemClicked != null) {
                    service().onServiceItemClicked?.invoke(
                        service()
                    )
                }
                .padding(horizontal = horizontalPadding)
                .padding(
                    top = MaterialTheme.dimens.innerPaddingLarge,
                    bottom = containerBottomPadding()
                ),
        ) {

            val (name, nameSpacer, price, priceSpacer, time, timeSpacer, estimateIcon, addServiceBtn, branchName, desc, deleteServiceBtn, serviceValidationAlert, branchSpacer) = createRefs()
            val (employeeImage, employeeNameTxt, changeEmployeeBtn, employeeValidationAlert, horizontalDivider, status, flag, deleteSelectedEmployee) = createRefs()

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .shimmer(service().showShimmer, shimmerModifier = Modifier.width(100.dp))
                    .constrainAs(name) {
                        linkTo(parent.start, parent.end)
                        top.linkTo(parent.top)
                        end.linkTo(serviceValidationAlert.start)
                        width = Dimension.preferredWrapContent
                    },
                text = service().title ?: "",
                textAlign = TextAlign.Start, overflow = TextOverflow.Ellipsis, maxLines = 2,
                style = MaterialTheme.typography.subtitle1.copy(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700
                ),
            )

            IconButton(
                onClick = {
                    multipleEventsCutter.clickWithThrottle {
                        service().onRemoveFromCart.invoke(service())
                    }
                },
                modifier = Modifier
                    .clip(CircleShape)
                    .size(MaterialTheme.dimens.iconSizeXLarge)
                    .shimmer(service().showShimmer)
                    .constrainAs(deleteServiceBtn) {
                        end.linkTo(parent.end, (-12).dp)
                        top.linkTo(name.top, (-8).dp)
                        visibility = deleteBtnVisibility
                    },
            ) {

                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.delete_icon),
                    contentDescription = "",
                    modifier = Modifier.padding(MaterialTheme.dimens.innerPaddingXXXSmall)

                )
            }

            IconButton(
                onClick = {
                    multipleEventsCutter.clickWithThrottle {
//                    TODO()
                        service().validationMessages?.firstOrNull()?.let {
                            service().onConflictClicked.invoke(it)
                        }
                    }
                },
                modifier = Modifier
                    .clip(CircleShape)
                    .size(MaterialTheme.dimens.iconSizeXLarge)
                    .shimmer(service().showShimmer)
                    .constrainAs(serviceValidationAlert) {
                        end.linkTo(deleteServiceBtn.start)
                        top.linkTo(name.top, (-8).dp)
                        visibility = validationServiceAlertVisibility
                    },
            ) {

                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_error_alert),
                    tint = MaterialTheme.colors.secondary,
                    contentDescription = "",
                    modifier = Modifier.padding(MaterialTheme.dimens.innerPaddingXXXSmall)

                )
            }

            Spacer(modifier = Modifier
                .height(
                    MaterialTheme.dimens.spaceBetweenItemsMedium +
                            MaterialTheme.dimens.spaceBetweenItemsSmall
                )
                .constrainAs(nameSpacer) {
                    linkTo(parent.start, parent.end)
                    top.linkTo(name.bottom)
                })

            TextStartsWithIcon(
                iconSize = 16.dp,
                modifier = Modifier
                    .shimmer(
                        service().showShimmer,
                        shimmerModifier = Modifier.width(20.dp)
                    )
                    .constrainAs(price) {
                        linkTo(parent.start, parent.end, endMargin = addIconWidth.plus(8.dp))
                        top.linkTo(nameSpacer.bottom)
                        width = Dimension.fillToConstraints
                    },
                drawableRes = R.drawable.price_icon,
                data = fees()
            )

            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.dimens.spaceBetweenItemsXSmall)
                    .constrainAs(priceSpacer) {
                        linkTo(parent.start, parent.end)
                        top.linkTo(price.bottom)
                    })


            TextStartsWithIcon(
                modifier = Modifier
                    .shimmer(
                        service().showShimmer,
                        shimmerModifier = Modifier.width(80.dp)
                    )
                    .constrainAs(time) {
                        start.linkTo(parent.start)
                        top.linkTo(priceSpacer.bottom)
                    },
                drawableRes = R.drawable.time_icon,
                iconTint = MaterialTheme.colors.secondary,
                data = min.invoke()

            )

            Spacer(modifier = Modifier
                .padding(start = MaterialTheme.dimens.spaceBetweenItemsXSmall)
                .constrainAs(timeSpacer) {
                    start.linkTo(time.end)
                    linkTo(time.top, time.bottom)
                })


            ImageFromRes(
                imageId = R.drawable.estimate_duration_icon,
                modifier = Modifier
                    .shimmer(service().showShimmer)
                    .constrainAs(estimateIcon) {
                        start.linkTo(
                            timeSpacer.end,

                            )
                        linkTo(time.top, time.bottom)
                        visibility = approximateTimeVisibility
                    }
                    .padding(end = MaterialTheme.dimens.screenGuideDefault)

            )


            if (service().type is ServiceType.ServiceWithAddButton && !service().showShimmer) {
                IconButton(
                    onClick = {
                        multipleEventsCutter.clickWithThrottle {
                            service().onAddServiceToCart(service())
                        }
                    },
                    enabled = addedInCart.not(),
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.xxSmall)
                        .size(addIconWidth)
                        .background(addedServiceBackground())
                        .shimmer(service().showShimmer)
                        .constrainAs(addServiceBtn) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        },
                ) {
                    Image(
                        painter = painterResource(id = addedServiceIcon),
                        contentDescription = ""
                    )
                }
            }


            TextStartsWithIcon(
                modifier = Modifier
                    .shimmer(
                        service().showShimmer,
                        shimmerModifier = Modifier.width(80.dp)
                    )
                    .constrainAs(branchName) {
                        linkTo(parent.start, parent.end)
                        top.linkTo(time.bottom, 6.dp, 6.dp)
                        width = Dimension.fillToConstraints
                        visibility = branchNameVisibility
                    },
                drawableRes = R.drawable.branch_icon,
                data = service().branch?.name ?: ""
            )

//        Spacer(modifier = Modifier
//            .height(MaterialTheme.dimens.spaceBetweenItemsXSmall)
//            .constrainAs(descSpacer) {
//                linkTo(parent.start, parent.end)
//                top.linkTo(branchName.bottom)
//                width = Dimension.fillToConstraints
//            })

            TextStartsWithIcon(
                modifier = Modifier
                    .shimmer(
                        service().showShimmer,
                        shimmerModifier = Modifier.width(80.dp)
                    )
                    .constrainAs(desc) {
                        linkTo(parent.start, addServiceBtn.start, endMargin = defaultGuide)
                        top.linkTo(branchName.bottom, 6.dp, 6.dp)
                        width = Dimension.fillToConstraints
                        visibility = descriptionVisibility
                    },
                drawableRes = R.drawable.description_icon,
                data = service().description ?: "",
                iconTint = MaterialTheme.colors.secondary
            )

            if (service().showEmployeeRow) {
                HorizontalDivider(
                    modifier = Modifier.constrainAs(horizontalDivider) {
                        top.linkTo(desc.bottom)
                        start.linkTo(parent.start)
                        width = Dimension.matchParent

                    },
                    padding = PaddingValues(
                        vertical = MaterialTheme.dimens.spaceBetweenItemsLarge
                    )
                )

            ImageItem(
                image = employee?.image,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(MaterialTheme.dimens.personImageSizeSmall)
                    .alpha(employeeConflictAlpha)
                    .shimmer(service().showShimmer)
                    .constrainAs(employeeImage) {
                        linkTo(parent.start, employeeNameTxt.start, bias = 0F)
                        top.linkTo(horizontalDivider.bottom)
                        bottom.linkTo(parent.bottom, 0.dp)
                        //  visibility = assignedEmployeeVisibility
                    },
                placeholder = R.drawable.ic_anyone
            )

                Text(
                    modifier = Modifier
                        //.fillMaxWidth()
                        .alpha(employeeConflictAlpha)
                        .shimmer(service().showShimmer)
                        .constrainAs(employeeNameTxt) {
                            linkTo(
                                employeeImage.end, deleteSelectedEmployee.start,
                                bias = 0F
                            )
                            top.linkTo(horizontalDivider.bottom)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.preferredWrapContent

                        }
                        .padding(
                            end = MaterialTheme.dimens.spaceBetweenItemsXSmall,
                            start = MaterialTheme.dimens.spaceBetweenItemsSmall
                        ),
                    text = employeeName(),
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.W500,
                        fontSize = 12.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start
                )


                IconButton(
                    modifier = Modifier
                        .padding(start = MaterialTheme.dimens.spaceBetweenItemsXSmall)
                        .size(MaterialTheme.dimens.iconSizeXXSmall)
                        .constrainAs(deleteSelectedEmployee) {
                            linkTo(employeeNameTxt.end, employeeValidationAlert.start, bias = 0F)
                            top.linkTo(changeEmployeeBtn.top)
                            bottom.linkTo(changeEmployeeBtn.bottom)
                            visibility = deleteSelectedEmployeeVisibility
                        }, onClick = {
                        multipleEventsCutter.clickWithThrottle {
                            service().deleteSelectedEmployee?.invoke(service())
                        }
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.close_icon),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }

                IconButton(
                    onClick = {
                        multipleEventsCutter.clickWithThrottle {
                            val validation = service().validationMessages?.find { it.requiredAction?.key == CartValidationActionType.ChangeEmployee.key }
                            validation?.let {
                                service().onConflictClicked.invoke(validation)
                            }
                        }
                    },
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(MaterialTheme.dimens.iconSizeXLarge)
                        .shimmer(service().showShimmer)
                        .constrainAs(employeeValidationAlert) {
                            linkTo(deleteSelectedEmployee.end, changeEmployeeBtn.start, bias = 0F)
                            top.linkTo(changeEmployeeBtn.top)
                            bottom.linkTo(changeEmployeeBtn.bottom)
                            visibility = validationEmployeeAlertVisibility
                        },
                ) {

                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_error_alert),
                        tint = MaterialTheme.colors.secondary,
                        contentDescription = "",
                        modifier = Modifier.padding(MaterialTheme.dimens.innerPaddingXXXSmall)

                    )
                }
                Row(
                    modifier = Modifier
                        .constrainAs(status) {
                            linkTo(employeeImage.top, employeeImage.bottom, bottomGoneMargin = 6.dp)
                            linkTo(
                                changeEmployeeBtn.end,
                                parent.end,
                                bias = 1F
                            )
                            visibility = statusVisibility
                        }, verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = service().status?.name?.let { stringResource(id = it).capitalize() } ?:"",
                        style = MaterialTheme.typography.caption.copy(
                            color =  MaterialTheme.colors.onBackground,
                            fontWeight = FontWeight.W500
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                    )

                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.spaceBetweenItemsMedium))

                    service().status?.icon?.let {
                        ImageFromRes(
                            imageId = it,
                            modifier = Modifier
                                .size(MaterialTheme.dimens.iconSizeSmall)
                                .clip(MaterialTheme.shapes.xxxSmall)
                                .background(MaterialTheme.colors.background)
                                .then(statusIconInnerPadding()),
                            tintColor = MaterialTheme.colors.onBackground
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.xxSmall)
                        .clickWithThrottle(onClick = {
                            service().reportEmployeeDidNotShowClicked.invoke(
                                service()
                            )
                        })
                        .padding(reportBtnInnerPadding())
                        .constrainAs(flag) {
                            linkTo(employeeImage.top, employeeImage.bottom, bottomGoneMargin = 6.dp)
                            end.linkTo(parent.end, (-8).dp)
                            visibility = reportIconVisibility
                        },
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.flag_icon),
                        contentDescription = "",
                        modifier = Modifier.size(MaterialTheme.dimens.iconSizeMedium)
                    )
                }


                IconButton(
                    modifier = Modifier
                        .size(MaterialTheme.dimens.iconSizeMedium)
                        .constrainAs(changeEmployeeBtn) {
                            top.linkTo(horizontalDivider.bottom)
                            bottom.linkTo(parent.bottom)
                            linkTo(employeeValidationAlert.end, parent.end, bias = 1F)
                            visibility = changeBtnVisibility
                        },
                    onClick = {
                        multipleEventsCutter.clickWithThrottle {
                            service().onChangeEmployeeClicked(service())
                        }
                    },
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary

                    )
                }


//                CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
//
//                OutlinedButton(modifier = Modifier
//                    .constrainAs(changeEmployeeBtn) {
//                        top.linkTo(horizontalDivider.bottom)
//                        bottom.linkTo(parent.bottom)
//                        end.linkTo(parent.end)
//                        visibility = changeBtnVisibility
//                    }
//                    .defaultMinSize(
//                        minWidth = ButtonDefaults.MinWidth,
//                        minHeight = 1.dp
//                    ),
//                    border = BorderStroke(
//                        ButtonDefaults.OutlinedBorderSize,
//                        MaterialTheme.colors.primary
//                    ),
//                    shape = MaterialTheme.shapes.xxSmall,
//                    contentPadding = PaddingValues(
//                        horizontal = MaterialTheme.dimens.innerPaddingXXXLarge,
//                        vertical = MaterialTheme.dimens.innerPaddingXSmall
//                    ),
//                    onClick = {
//                        multipleEventsCutter.clickWithThrottle {
//                            service().onChangeEmployeeClicked(service())
//                        }
//                    }
//                ) {
//                    Text(
//                        modifier = Modifier,
//                        text = stringResource(id = R.string.change),
//                        style = MaterialTheme.typography.button.copy(
//                            color = MaterialTheme.colors.primary,
//                            fontWeight = FontWeight.W400,
//                            fontSize = 12.sp
//                        ), maxLines = 1, overflow = TextOverflow.Ellipsis
//                    )
//                }
//            }


//            ClearButton(text = R.string.change, modifier = Modifier.constrainAs(changeEmployeeBtn) {
//                top.linkTo(employeeImage.top)
//                bottom.linkTo(employeeImage.bottom)
//                end.linkTo(parent.end)
//            },
//            backgroundColor = MaterialTheme.colors.surface) {

//            }
            }
            createHorizontalChain(
                employeeImage, employeeNameTxt,deleteSelectedEmployee, employeeValidationAlert,
                chainStyle = ChainStyle.Packed)
        }


    }

}