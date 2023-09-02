package com.trianglz.mimar.modules.appointments_list.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.common.presentation.pagination.ComposePaginatedList
import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.MimarPlaceholder
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.modules.appointments.presentation.composables.AppointmentItem
import com.trianglz.mimar.modules.appointments.presentation.model.AppointmentUIModel

@Composable
fun AppointmentsPaginatedList(
    source: () -> ComposePaginatedListDataSource<AppointmentUIModel>,
    selectedAppointmentText: () -> Int?,
    onRefresh: () -> Unit,

    ) {


    val state = rememberLazyListState()

    LaunchedEffect(key1 = source().loadingState.value) {
        if (source().loadingState.value.isLoadingInitial) {
            state.scrollToItem(0)
        }
    }

    ComposePaginatedList(
        state = state,
        onRefresh = onRefresh,
        lazyListModifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(MaterialTheme.dimens.screenGuideDefault),
        paginatedListSource = source(),
        spacingBetweenItems = MaterialTheme.dimens.spaceBetweenItemsMedium,
        listItem = { _, item ->
            AppointmentItem(appointment = { item })
        },
        disableScrollToFirstItem = true,
        emptyListPlaceholder = {

            Column(
                modifier = Modifier.fillParentMaxSize()
            ) {
                MimarPlaceholder(
                    modifier = {
                        Modifier
                            .fillParentMaxWidth(1f)
                            .fillParentMaxHeight(1f)
                    },
                    placeholderImage = R.drawable.calender_placeholder,
                    titleFirstText = { R.string.no },
                    titleSecondText = { R.string.appointments },
                    descriptionText = {
                        StringWrapper { context ->
                            return@StringWrapper context.getString(
                                R.string.there_are_no_appointments,
                                selectedAppointmentText()?.let { context.getString(it).lowercase() }
                            )
                        }
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(MaterialTheme.dimens.bottomNavigationHeight)
            )
        }
    )


}