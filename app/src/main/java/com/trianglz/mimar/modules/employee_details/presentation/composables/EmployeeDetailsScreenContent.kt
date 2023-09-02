package com.trianglz.mimar.modules.employee_details.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.core_compose.presentation.swipe_refresh.ComposeSwipeRefresh
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.*
import com.trianglz.mimar.common.presentation.models.ProfileHeaderInfoModel
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.common.presentation.ui.theme.spaceBetweenItemsXXLarge
import com.trianglz.mimar.modules.employee_details.presentation.mapper.convertSpecialitiesToMap
import com.trianglz.mimar.modules.employee_details.presentation.model.EmployeeDetailsUIModel

@Composable
fun EmployeeDetailsScreenContent(
    navigator: DestinationsNavigator? = null,
    employee: () -> EmployeeDetailsUIModel,
    showNetworkError: () -> Boolean,
    isRefreshing: () -> Boolean,
    isWorkingHoursExpanded: () -> MutableState<Boolean>,
    isSupportedAreasExpanded: () -> MutableState<Boolean>,
    isServicesExpanded: () -> MutableState<Boolean>,
    onSwipeToRefresh: () -> Unit,
    branchName: String
) {


    Box(
        modifier = Modifier
            .calculateBottomPadding(MaterialTheme.dimens.bottomNavigationHeight)
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {

        // screen content
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ScreenHeader(
                navigator = navigator,
                header = { StringWrapper(branchName) },
                contentColor = MaterialTheme.colors.surface,
                isAddPadding = { false },
                modifier = Modifier
                    .background(MaterialTheme.colors.primary)
                    .statusBarsPadding()
                    .padding(top = MaterialTheme.dimens.spaceBetweenItemsMedium)
                    .padding(bottom = MaterialTheme.dimens.spaceBetweenItemsXXLarge)
                    .background(MaterialTheme.colors.primary)
            )

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
                    ) {

                        ProfileHeader(
                            ProfileHeaderInfoModel(
                                employee().userName,
                                employee().image,
                                employee().rating?.toInt(),
                                employee().showShimmer,
                            )
                        )
                        if (employee().employeeWorkingHours.isNullOrEmpty().not() || employee().showShimmer) {

                            WorkingHoursExpandableItem(
                                { employee().employeeWorkingHours },
                                isWorkingHoursExpanded, { employee().showShimmer },
                                R.string.day_off,
                                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideDefault)

                            )
                        }


                        if (employee().offeredLocation != null || employee().showShimmer) {

                            ExpandableCard(
                                title = { StringWrapper(R.string.offered_locations) },
                                icon = { ImageVector.vectorResource(R.drawable.ic_map_outlined) },
                                isExpanded = isSupportedAreasExpanded,
                                includeVerticalSpacingInExpandedContent = { false },
                                isLoading = { employee().showShimmer },
                                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideDefault)

                            ) {
                                if (!employee().showShimmer) {

                                    HorizontalDivider(padding = PaddingValues(bottom = MaterialTheme.dimens.innerPaddingMedium))

                                    employee().offeredLocationList?.forEach {
                                        OfferedLocationItem(it)
                                    }
                                }
                            }
                        }

                        // Services
                        if (employee().services.isNullOrEmpty().not() || employee().showShimmer) {
                            ExpandableCard(
                                title = { StringWrapper(R.string.offered_services) },
                                icon = { ImageVector.vectorResource(R.drawable.service_icon) },
                                isExpanded = isServicesExpanded,
                                includeVerticalSpacingInExpandedContent = { false },
                                isLoading = { employee().showShimmer },
                                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
                            ) {
                                if (!employee().showShimmer) {
                                    HorizontalDivider(padding = PaddingValues(bottom = MaterialTheme.dimens.innerPaddingMedium))

                                    employee().services?.convertSpecialitiesToMap()?.let {

                                        for ((index, key) in it.keys.withIndex()) {

                                            val isLastService by remember {
                                                derivedStateOf {
                                                    it.size.minus(1) == index
                                                }
                                            }

                                            EmployeeServiceItem(
                                                title = { key },
                                                data = { it[key] ?: emptyList() }
                                            )
                                            if (!isLastService) {
                                                HorizontalDivider(padding = PaddingValues(bottom = MaterialTheme.dimens.innerPaddingMedium))
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideDefault))
                    }


                }
            }
        }
    }
}