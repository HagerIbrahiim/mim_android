package com.trianglz.mimar.modules.ratings.presenation.composables.appointment_review

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.bottom_sheet.BottomSheetRoundedContainerWithButton
import com.trianglz.mimar.common.presentation.compose_views.bottom_sheet.BottomSheetTopSection
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState
import com.trianglz.mimar.modules.ratings.presenation.RatingViewModel
import com.trianglz.mimar.modules.ratings.presenation.composables.appointment_review.cells.AppointmentFeedbackItem
import com.trianglz.mimar.modules.ratings.presenation.composables.appointment_review.cells.RateEmployeeItem
import com.trianglz.mimar.modules.ratings.presenation.composables.appointment_review.cells.ReviewAppointmentThankYouItem
import com.trianglz.mimar.modules.ratings.presenation.contract.RatingEvent
import com.trianglz.mimar.modules.ratings.presenation.contract.RatingState
import com.trianglz.mimar.modules.ratings.presenation.contract.RatingViewState
import com.trianglz.mimar.modules.ratings.presenation.model.ReviewAppointmentSheetData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReviewAppointmentBottomSheet(
    viewModel: RatingViewModel = hiltViewModel(),
    feedback: () -> TextFieldState,
    data: () -> ReviewAppointmentSheetData,
    isBottomSheetOpened: () -> Boolean,
    onCloseBottomSheetClicked: () -> Unit,
    refreshAppointment: () -> Unit,
) {

    LaunchedEffect(key1 = isBottomSheetOpened()){
        viewModel.setEvent(RatingEvent.SetSheetData(data()))
    }

    LaunchedEffect(key1 = feedback()){
        viewModel.viewStates?.feedback = feedback()
    }


    val viewStates = remember {
        viewModel.viewStates ?: RatingViewState()
    }

    val onPrimaryButtonClicked = remember {
        {
            onCloseBottomSheetClicked()
            viewModel.setEvent(RatingEvent.SubmitReviewBtnClicked)
        }
    }

    val bottomSheetContentBottomPadding: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.buttonHeight + MaterialTheme.dimens.spaceBetweenItemsXLarge
        }
    }

    BottomSheetRoundedContainerWithButton(
        isPrimaryButtonEnabled = { viewStates.isContentValid.value },
        primaryButtonText = { R.string.submit },
        secondaryButtonText = { R.string.cancel },
        onPrimaryButtonClicked = onPrimaryButtonClicked,
        onSecondaryButtonClicked = onCloseBottomSheetClicked,
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = bottomSheetContentBottomPadding()),
            modifier = Modifier.padding(bottom = MaterialTheme.dimens.spaceBetweenItemsSmall * 2),
        ) {
            stickyHeader {
                Column(modifier = Modifier.background(MaterialTheme.colors.surface)) {
                    BottomSheetTopSection(
                        title = { StringWrapper() },
                        topPadding = MaterialTheme.dimens.screenGuideLarge,
                        onBackButtonClicked = onCloseBottomSheetClicked
                    )

                    ReviewAppointmentThankYouItem()
                }

            }

            items(viewStates.list?.value ?: listOf()) {
                RateEmployeeItem { it }
            }

            item {
                AppointmentFeedbackItem(feedback)
            }

        }
    }

    GeneralObservers<RatingState, RatingViewModel>(viewModel = viewModel) {
        when (it) {
            RatingState.RefreshAppointment -> {
                refreshAppointment()
            }
        }
    }

    HandleLoadingStateObserver(viewModel = viewModel) {
    }

}