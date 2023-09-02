package com.trianglz.mimar.modules.branch_view_all.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.domain.extensions.formatDate
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.enums.Locales
import com.trianglz.core.presentation.utils.getAppLocale
import com.trianglz.core_compose.presentation.composables.HeaderShadow
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.core_compose.presentation.extensions.setStatusBarPadding
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.core_compose.presentation.swipe_refresh.ComposeSwipeRefresh
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.common.presentation.ui.theme.dividerColor
import com.trianglz.mimar.modules.branches.presentation.composables.BranchItem
import com.trianglz.mimar.common.presentation.models.CoveredZonesUIModel
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.common.presentation.models.WorkingHoursUIModel
import com.trianglz.mimar.modules.employee.presentation.composables.EmployeeItem
import com.trianglz.mimar.modules.employee.presentation.model.EmployeeUIModel
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import com.trianglz.mimar.common.domain.enum.SimpleDateFormatData.Day
import com.trianglz.mimar.common.domain.enum.SimpleDateFormatData.DayEn
import com.trianglz.mimar.common.presentation.compose_views.*

@Composable
fun BranchViewAllScreenContent(
    navigator: DestinationsNavigator?,
    userModeHandler: UserModeHandler,
    branchName: () -> String,
    lat: () -> Double,
    lng: () -> Double,
    showMap: () -> Boolean,
    locationText: () -> StringWrapper,
    workingHours: () -> List<WorkingHoursUIModel>,
    staff: () -> SnapshotStateList<EmployeeUIModel>?,
    areas: () -> List<CoveredZonesUIModel>,
    otherBranches: () -> SnapshotStateList<BranchUIModel>?,
    isOtherBranchesExpanded: () -> MutableState<Boolean>,
    isStaffExpanded: () -> MutableState<Boolean>,
    isWorkingHoursExpanded: () -> MutableState<Boolean>,
    isSupportedAreasExpanded: () -> MutableState<Boolean>,
    onMapClicked: () -> Unit,
    showNetworkError: () -> Boolean,
    isRefreshing: () -> Boolean,
    isLoading: () -> Boolean,
    onSwipeToRefresh: () -> Unit,
    notificationsCount: State<Int>,
    cartCount: State<Int>,
) {


    val formattedWeekDay : (weekDay: String) -> String? = remember(workingHours()) {
        {
            if (getAppLocale() == Locales.ARABIC.code) {
                it.uppercase().formatDate(
                    DayEn,
                    Day,
                )
            } else it.capitalize()
        }
    }

    val workingDay: (workingHours: WorkingHoursUIModel, index: Int) -> String? = remember {
        { workingHours, index ->
            if (index == 0) {
                formattedWeekDay(workingHours.weekDay)
            } else null
        }
    }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .setStatusBarPadding()
            .calculateBottomPadding(MaterialTheme.dimens.bottomNavigationHeight)
    ) {
        HeaderShadow(dividerColor = MaterialTheme.colors.dividerColor) { _ ->
            ScreenHeader(
                navigator = navigator,
                userModeHandler = userModeHandler,
                header = { StringWrapper(stringMessage = branchName()) },
                modifier = Modifier.fillMaxWidth(),
                isAddBackButton = { true },
                isAddPadding = { false },
                showCartIcon = { true },
                showNotificationIcon = { true },
                showShimmer = isLoading,
                notificationsCount = notificationsCount,
                cartCount = cartCount
            )
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
                isRefreshing = isRefreshing(), onRefresh = {
                    onSwipeToRefresh.invoke()
                }
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.innerPaddingSmall),
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(MaterialTheme.dimens.screenGuideDefault)
                ) {
                    // Map
                    if(showMap()) {
                        BranchMapItem(
                            locationText = locationText,
                            lat,
                            lng,
                            isLoading,
                            onItemClicked = onMapClicked
                        )
                    }


                    //Supported Areas
                    if (areas().isNotEmpty()) {
                        ExpandableCard(
                            title = { StringWrapper(R.string.supported_areas) },
                            icon = { ImageVector.vectorResource(R.drawable.ic_map_outlined) },
                            isLoading = isLoading,
                            isExpanded = isSupportedAreasExpanded,

                        ) {
                            HorizontalDivider(padding = PaddingValues(0.dp))
                            Text(
                                text = stringResource(R.string.support_home_services_at),
                                style = MaterialTheme.typography.body2.copy(
                                    color = MaterialTheme.colors.primary, fontWeight = W600
                                ),
                                maxLines = 2,
                                textAlign = TextAlign.Start,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.shimmer(isLoading())
                            )
                            areas().forEach {
                                SupportedAreaItem({ it }, isLoading)
                            }
                        }
                    }

                    // Working Hours

                    WorkingHoursExpandableItem(workingHours,isWorkingHoursExpanded,isLoading,)

                    //Staff
                    if (!staff().isNullOrEmpty()) {
                        ExpandableCard(
                            title = { StringWrapper(R.string.staff) },
                            icon = { ImageVector.vectorResource(R.drawable.ic_staff_outlined) },
                            isLoading = isLoading,
                            includeVerticalSpacingInExpandedContent = { false },
                            isExpanded = isStaffExpanded,
                        ) {
                            staff()?.forEach {
                                HorizontalDivider(padding = PaddingValues(0.dp))
                                EmployeeItem({ it }, isLoading)
                            }
                        }

                    }

                    // Other Branches

                    if (!otherBranches().isNullOrEmpty()) {
                        ExpandableCard(
                            title = { StringWrapper(R.string.other_branches) },
                            icon = { ImageVector.vectorResource(R.drawable.ic_shop_outlined) },
                            isLoading = isLoading,
                            isExpanded = isOtherBranchesExpanded,
                            ) {

                            otherBranches()?.forEachIndexed { index, branch ->
                                if(index == 0) {
                                    HorizontalDivider(padding = PaddingValues(0.dp))
                                }

                                BranchItem(
                                    item = { branch },
                                    backgroundColor = { MaterialTheme.colors.background })
                            }
                        }
                    }

                }
            }


        }

    }
}