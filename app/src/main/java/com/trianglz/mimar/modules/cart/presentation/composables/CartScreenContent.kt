package com.trianglz.mimar.modules.cart.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.composables.HeaderShadow
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.*
import com.trianglz.mimar.common.presentation.models.AnnotatedTextModel
import com.trianglz.mimar.common.presentation.tabs.compose_views.MimarTabs
import com.trianglz.mimar.common.presentation.ui.theme.*
import com.trianglz.mimar.modules.calendar.presentation.composables.WeekViewCalendar
import com.trianglz.mimar.modules.cart.presentation.composables.time_section.AvailableSlotsItem
import com.trianglz.mimar.modules.cart.presentation.model.*
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType
import com.trianglz.mimar.modules.services.presentation.composables.ServiceItem
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler

@Composable
fun CartScreenContent(
    list: SnapshotStateList<BaseCartUIModel>,
    navigator: DestinationsNavigator?,
    userModeHandler: UserModeHandler,
    isRefreshing: @Composable () -> Boolean,
    showNetworkError: @Composable () -> Boolean,
    emptyCart: MutableState<Boolean>,
    onLocationTabChanged: (Int) -> Unit,
    onPaymentChanged: (Int) -> Unit,
    selectedOfferedLocationType: State<OfferedLocationType?>,
    onChangeAddressClicked: () -> Unit,
    onAddAnotherServiceClicked: () -> Unit,
    onAddAddressClicked: () -> Unit,
    onConflictClicked: (ValidationMessageUIModel) -> Unit,
    onConfirmAppointmentClicked: () -> Unit,
    onOpenDiscoverScreenClicked: () -> Unit,
    refreshScreen: () -> Unit,
    notificationsCount: State<Int>,
    cartCount: State<Int>,
) {
    val context = LocalContext.current

    val offeredLocationType by remember {
        selectedOfferedLocationType
    }

    val showPlaceholder by remember {
        emptyCart
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .statusBarsPadding()
            .calculateBottomPadding(MaterialTheme.dimens.bottomNavigationHeight)
    ) {
        HeaderShadow(dividerColor = MaterialTheme.colors.dividerColor) { _ ->
            ScreenHeader(
                navigator = navigator,
                userModeHandler = userModeHandler,
                header = { StringWrapper(R.string.book_an_appointment) },
                modifier = Modifier.fillMaxWidth(),
                isAddBackButton = { true },
                isAddPadding = { false },
                showCartIcon = { false },
                showNotificationIcon = { true },
                notificationsCount = notificationsCount,
                cartCount = cartCount
            )
        }
//        SearchTabs(
//            modifier = { Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideDefault) },
//            showProviders = { false },
//            onTabChanged = {  },
//        )
        if (showNetworkError()) {
            BaseComposeMainUIComponents.LocalMainComponent.NetworkError(
                modifier = Modifier,
                addPadding = false
            ) {
                refreshScreen()
            }
        } else if (showPlaceholder) {
            MimarPlaceholder(
                modifier = {
                    Modifier
                        .weight(1f)
                        .padding(all = MaterialTheme.dimens.screenGuideDefault)
                },
                animationFile =  R.raw.cart,
                titleFirstText = { R.string.cart },
                titleSecondText = { R.string.is_empty },
                customDescription = {
                    val texts = remember {
                        listOf(
                            AnnotatedTextModel(
                                StringWrapper(R.string.your_cart_is_empty_no_added_services_yet),
                                style = { MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary) }
                            ),

                            AnnotatedTextModel(
                                StringWrapper(R.string.discover_providers_services),
                                style = {

                                    MaterialTheme.typography.body1.copy(
                                        color = MaterialTheme.colors.secondary,
                                        textDecoration = TextDecoration.Underline
                                    )
                                },
                                onClick = onOpenDiscoverScreenClicked
                            ),
                        )
                    }

                    GeneralMultiColorPartiallyClickableText(
                        modifier = Modifier,
                        texts = texts,
                        maxLines = Int.MAX_VALUE,
                        textAlign = TextAlign.Center
                    )

                },
            ) {

            }
        } else {
            if (isRefreshing().not()) {
                LazyColumn() {
                    items(list, key = { it.uniqueId }) { item ->
                        when (item) {
                            is CartLocationSectionUIModel -> {
                                Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsDefault))
                                if (item.locations.size > 0) {
                                    MimarTabs(
                                        modifier = { Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideDefault) },
                                        list = {
                                            item.locations
                                        },
                                        indicatorBackground = {
                                            Brush.horizontalGradient(
                                                colors = listOf(
                                                    MaterialTheme.colors.primary,
                                                    Sycamore,
                                                    MaterialTheme.colors.primary,
                                                )
                                            )
                                        },
                                        tabsBackground = { MaterialTheme.colors.onPrimary },
//                    showProviders = { showProvidersTab()  },
                                        onTabChanged = onLocationTabChanged,
                                    )
                                }
                            }
                            is CartSectionTitleUIModel -> {
//                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsLarge))
                                Text(
                                    text = item.title.getString(context),
                                    modifier = Modifier.padding(
                                        vertical = MaterialTheme.dimens.spaceBetweenItemsXLarge,
                                        horizontal = MaterialTheme.dimens.screenGuideDefault
                                    ),
                                    textAlign = TextAlign.Start,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.subtitle2.copy(
                                        fontWeight = FontWeight.W600
                                    )
                                )
//                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXLarge))
                            }
                            is CartCalendarUIModel -> {
                                item.calendar.value?.let {
                                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsLarge))
                                    WeekViewCalendar { it }
                                    HorizontalDivider(
                                        padding = PaddingValues(
                                            start = MaterialTheme.dimens.screenGuideDefault,
                                            end = MaterialTheme.dimens.screenGuideDefault,
                                            top = MaterialTheme.dimens.spaceBetweenItemsLarge
                                        )
                                    )

                                }
                            }
                            is CartAvailableTimesUIModel -> {
//                        CartSection(stringResource(R.string.available_slots)) {
                                AvailableSlotsItem(item.availableTimeSlots)
//                        }
                                HorizontalDivider(
                                    padding = PaddingValues(
                                        start = MaterialTheme.dimens.screenGuideDefault,
                                        end = MaterialTheme.dimens.screenGuideDefault,
                                        top = MaterialTheme.dimens.spaceBetweenItemsLarge
                                    )
                                )
                            }
                            is ServiceUIModel -> {
                                ServiceItem(
                                    modifier = Modifier
                                        .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
                                        .padding(bottom = MaterialTheme.dimens.spaceBetweenItemsMedium)
                                ) { item }
                            }
                            is CartAddAnotherServiceUIModel -> {
                                Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsMedium))
                                AddAnotherServiceSection(
                                    item.canAddAnotherService,
                                    onAddAnotherServiceClicked,
                                    onConflictClicked
                                )
                                HorizontalDivider(
                                    padding = PaddingValues(
                                        start = MaterialTheme.dimens.screenGuideDefault,
                                        end = MaterialTheme.dimens.screenGuideDefault,
                                        top = MaterialTheme.dimens.spaceBetweenItemsLarge
                                    )
                                )

                            }
                            is CartNoteSectionUIModel -> {
                                item.noteUIModel.value?.let {
//                            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsMedium))
                                    CartNoteSection(it)
                                    HorizontalDivider(
                                        padding = PaddingValues(
                                            start = MaterialTheme.dimens.screenGuideDefault,
                                            end = MaterialTheme.dimens.screenGuideDefault,
                                            top = MaterialTheme.dimens.spaceBetweenItemsLarge
                                        )
                                    )
                                }
                            }
                            is CartFooterSectionUIModel -> {
                                CartFooterSection(item, onConfirmAppointmentClicked)
                            }
                            is CartAddressSectionUIModel -> {
                                val showSection by remember {
                                    item.showSection
                                }
                                if (showSection) {
                                    CartAddressSection(
                                        item,
                                        onAddAddressClicked,
                                        onChangeAddressClicked
                                    )
                                    HorizontalDivider(
                                        padding = PaddingValues(
                                            start = MaterialTheme.dimens.screenGuideDefault,
                                            end = MaterialTheme.dimens.screenGuideDefault,
                                            top = MaterialTheme.dimens.spaceBetweenItemsLarge
                                        )
                                    )
                                }
                            }
                            is CartPaymentTypesSectionUIModel -> {
                                CartPaymentSection(item, onPaymentChanged)
                                HorizontalDivider(
                                    padding = PaddingValues(
                                        start = MaterialTheme.dimens.screenGuideDefault,
                                        end = MaterialTheme.dimens.screenGuideDefault,
                                        top = MaterialTheme.dimens.spaceBetweenItemsLarge
                                    )
                                )

                            }

                            is CartValidationSectionUIModel -> {
                                if (item.validationMessageUIModel.value != null) {
                                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsMedium))
                                    CartValidationSection {
                                        item
                                    }
                                    HorizontalDivider(
                                        padding = PaddingValues(
                                            start = MaterialTheme.dimens.screenGuideDefault,
                                            end = MaterialTheme.dimens.screenGuideDefault,
                                            top = MaterialTheme.dimens.spaceBetweenItemsLarge
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun CartSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(vertical = MaterialTheme.dimens.spaceBetweenItemsXXLarge)) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideDefault),
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle2.copy(
                fontWeight = FontWeight.W600
            )
        )
        content()
    }
}
