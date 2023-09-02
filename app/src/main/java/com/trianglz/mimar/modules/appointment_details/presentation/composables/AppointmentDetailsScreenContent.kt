package com.trianglz.mimar.modules.appointment_details.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.composables.HeaderShadow
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.core_compose.presentation.swipe_refresh.ComposeSwipeRefresh
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.ScreenHeader
import com.trianglz.mimar.common.presentation.ui.theme.*
import com.trianglz.mimar.modules.appointment_details.presentation.composables.cells.*
import com.trianglz.mimar.modules.appointment_details.presentation.model.AppointmentDetailsScreenMode
import com.trianglz.mimar.modules.appointment_details.presentation.model.AppointmentDetailsUIModel
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.modules.services.presentation.composables.ServiceItem
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler

@Composable
fun AppointmentDetailsScreenContent(
    navigator: DestinationsNavigator?,
    appointmentDetails: () -> AppointmentDetailsUIModel?,
    showNetworkError: @Composable () -> Boolean,
    notificationsCount: State<Int>,
    cartCount: State<Int>,
    userModeHandler: UserModeHandler,
    isRefreshing: () -> Boolean,
    onSwipeToRefresh: () -> Unit,
    onBackBtnClicked: () -> Unit,
    onCartIconClicked: () -> Unit,
) {


    val context = LocalContext.current


    val cancellationTopHeight: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.spaceBetweenItemsMedium * 3 + 4.dp
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .calculateBottomPadding()) {

        if (appointmentDetails()?.mode is AppointmentDetailsScreenMode.AppointmentDetails) {
            HeaderShadow(dividerColor = Iron) { _ ->

                ScreenHeader(
                    header = {
                        StringWrapper{appointmentDetails()?.appointmentNumber?.let {
                            this.getString(R.string.appointment_number,it)
                        } ?: run { "" }}
                    },
                    isAddPadding = { false },
                    userModeHandler = userModeHandler,
                    showCartIcon = { true },
                    showNotificationIcon = { true },
                    cartCount = cartCount,
                    notificationsCount = notificationsCount,
                    navigator = navigator,
                    onBackClicked = onBackBtnClicked,
                    onCartClicked = onCartIconClicked,
                )
            }
        }
        if (showNetworkError()) {
            BaseComposeMainUIComponents.LocalMainComponent.NetworkError(
                modifier = Modifier,
                addPadding = false
            ) {
                onSwipeToRefresh()
            }
        } else {
            ComposeSwipeRefresh(
                isRefreshing = isRefreshing(),
                onRefresh = {
                    onSwipeToRefresh.invoke()
                }, swipeEnabled = appointmentDetails()?.mode is AppointmentDetailsScreenMode.AppointmentDetails
            ) {
                appointmentDetails()?.let { appointment ->

                    LazyColumn(
                        contentPadding = PaddingValues(vertical = MaterialTheme.dimens.innerPaddingLarge),
                        modifier = Modifier
                            .fillMaxSize()
                            .calculateBottomPadding(MaterialTheme.dimens.bottomNavigationHeight)
                            .background(MaterialTheme.colors.background)
                    ) {

                        item {
                            if (appointment.mode is AppointmentDetailsScreenMode.AppointmentDetails)
                                AppointmentBranchData { appointment.branch ?: BranchUIModel() }
                            else
                                AppointmentThankYouMessage()
                        }

                        //region Appointment details section
                        item {
                            AppointmentDetailsSectionTitle(title = R.string.appointment_details,
                                isLoading = { appointment.showShimmer })
                        }

                        item {
                            AppointmentDetailsDataItem(
                                title = appointment.appointmentDetailsFirstSectionValue,
                                titleIcon = appointment.appointmentDetailsFirstSectionIcon,
                                primaryDescription = appointment.appointmentDetailsFirstSectionTitle.getString(
                                    context
                                ),
                                isLoading = { appointment.showShimmer },
                                shape = MaterialTheme.shapes.topRoundedCornerShapeMedium
                            )
                        }

                        item {
                            AppointmentDetailsDataItem(
                                title = R.string.appointment_date,
                                titleIcon = R.drawable.calender_icon,
                                primaryDescription = appointment.getFormattedDate() ?: "",
                                isLoading = { appointment.showShimmer },
                                shape = RectangleShape
                            )
                        }

                        if (appointment.mode is AppointmentDetailsScreenMode.ConfirmAppointment){
                            item {
                                AppointmentDetailsDataItem(
                                    title = R.string.branch_name,
                                    titleIcon = R.drawable.branch_icon,
                                    primaryDescription = appointment.branch?.name ?:"",
                                    isLoading = { appointment.showShimmer },
                                    shape = RectangleShape
                                )
                            }
                        }

                        item {
                            AppointmentDetailsDataItem(
                                title = R.string.location,
                                titleIcon = R.drawable.ic_location,
                                primaryDescription = appointment.location?.name?.getString(
                                    context
                                ) ?: "",
                                secondaryDescription = appointment.appointmentSelectedAddress,
                                isLoading = { appointment.showShimmer },
                                shape = MaterialTheme.shapes.bottomRoundedCornerShapeMedium,
                                modifier = Modifier.padding(bottom = MaterialTheme.dimens.innerPaddingSmall)
                            )
                        }


                        //endregion

                        //region Services
                        if (!appointment.appointmentBranchServices.isNullOrEmpty()) {
                            item {
                                AppointmentDetailsSectionTitle(title = R.string.services,
                                    isLoading = { appointmentDetails()?.showShimmer ?: false })
                            }

                            itemsIndexed(
                                items = appointment.appointmentBranchServices,
                                key = { _, item ->
                                    item.serviceIdInCart ?: -1
                                }) { index, item ->

                                val isLastService by remember(appointment.appointmentBranchServices) {
                                    derivedStateOf {
                                        appointment.appointmentBranchServices.size.minus(1) == index
                                    }
                                }

                                Column {
                                    ServiceItem(
                                        service = { item },
                                        modifier = Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
                                    )
                                    if (!isLastService) {
                                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsMedium))
                                    }
                                }

                            }
                        }

                        //endregion

                        //region Payment method
                        item {
                            AppointmentDetailsSectionTitle(title = R.string.payment_method,
                                isLoading = { appointmentDetails()?.showShimmer ?: false })
                        }
                        item {
                            AppointmentPaymentMethod(
                                data = { appointment.paymentMethod },
                                isLoading = { appointment.showShimmer },
                                onEditBtnClicked = appointment.changePaymentMethod,
                                showEditBtn = { appointment.showEditPaymentMethod }
                            )
                        }
                        //endregion

                        //region Cancellation policy
                        if (appointment.mode is AppointmentDetailsScreenMode.ConfirmAppointment) {

                            item {
                                Spacer(modifier = Modifier.height(cancellationTopHeight()))
                            }

                            item {
                                AppointmentCancellationPolicy(
                                    appointment.cancellationPolicyClicked
                                )
                            }
                        }

                        //endregion

                        //region Total Price
                        if (appointment.showTotalExactFees) {
                            
                            item {
                                AppointmentDetailsSectionTitle(title = R.string.payment_summary,
                                    isLoading = { appointmentDetails()?.showShimmer ?: false })
                            }

                            item {
                                AppointmentPaymentSummary(
                                    appointment.formattedCurrency() ,
                                    { appointment.totalCost },
                                    { appointment.totalCostWithoutVat },
                                    { appointment.vat },
                                    { appointment.vatAmount }
                                )
                            }
                        }
                        //endregion

                        //region Pending payment action button
                        if (appointment.showActionButtons) {
                            item {
                                Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXXXLarge))

                                AppointmentActionButtons(
                                    primaryBtnText = { appointment.primaryBtnText ?: R.string.review },
                                    secondaryBtnText = { appointment.secondaryBtnText },
                                    showSecondaryBtn = {appointment.showSecondaryBtn},
                                    primaryBtnClicked = appointment.primaryBtnClicked ?:{},
                                    secondaryBtnClicked = appointment.secondaryBtnClicked,
                                    isPrimaryBtnEnabled = { appointment.isPrimaryBtnEnabled ?: false },
                                    isSecondaryBtnEnabled = { appointment.isSecondaryBtnEnabled },
                                    )
                            }
                        }
                        //endregion


                        item {
                            Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideXLarge))
                        }
                    }

                }
            }
        }
    }

}


