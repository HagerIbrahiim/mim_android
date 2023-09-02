package com.trianglz.mimar.modules.filter.presenation.composables


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.bottom_sheet.BottomSheetRoundedContainerWithButton
import com.trianglz.mimar.common.presentation.compose_views.bottom_sheet.BottomSheetTopSection

@Composable
fun SelectDateBottomSheet(
    pickedDate: () -> String?,
    pickedTime: () -> String?,
    onSelectDateClicked: () -> Unit,
    onSelectTimeClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    onSubmitBtnClicked: () -> Unit,
    onSelectDateTrailingIconClicked: () -> Unit,
    onSelectTimeTrailingIconClicked: () -> Unit,
    ) {

    val isButtonDisabled = remember(pickedDate(), pickedTime()) {
        pickedTime() != null && pickedDate() == null
    }

    val spaceBetweenItemsDouble : @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.spaceBetweenItemsMedium * 2
        }
    }

    val dateBottomSheetBottomSpacing : @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.buttonHeight + MaterialTheme.dimens.spaceBetweenItemsXLarge * 2 +
                    MaterialTheme.dimens.spaceBetweenItemsSmall / 2
        }
    }

    val dateIcon = remember(pickedDate()) {
        if(pickedDate().isNullOrEmpty())
            R.drawable.ic_arrow_down
        else
            R.drawable.cancelled_icon
    }

    val timeIcon = remember(pickedTime()) {
        if(pickedTime().isNullOrEmpty())
            R.drawable.ic_arrow_down
        else
            R.drawable.cancelled_icon
    }

    BottomSheetRoundedContainerWithButton(
        primaryButtonText = { R.string.apply },
        isPrimaryButtonEnabled = { !isButtonDisabled },
        onPrimaryButtonClicked = onSubmitBtnClicked,

        ) {
        Column {

            BottomSheetTopSection(
                title = { StringWrapper(R.string.availability_time) },
                onBackButtonClicked = onBackButtonClicked,
            )

            Column(modifier = Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideDefault)) {
                ClickableDropDownField(
                    label = { R.string.date },
                    unSelectedText = { R.string.select_date },
                    selectedText = { pickedDate() ?: "" },
                    userChangedText = { pickedDate() != null },
                    startIcon = { R.drawable.date_icon },
                    endIcon = { dateIcon },
                    onItemClick = onSelectDateClicked,
                    onTrailingIconClicked = onSelectDateTrailingIconClicked,
                )

                Spacer(modifier = Modifier.height(spaceBetweenItemsDouble()))

                ClickableDropDownField(
                    label = { R.string.time },
                    unSelectedText = { R.string.select_time },
                    selectedText = { pickedTime() ?: "" },
                    userChangedText = { pickedTime() != null },
                    startIcon = { R.drawable.time_icon },
                    endIcon = { timeIcon },
                    onItemClick = onSelectTimeClicked,
                    onTrailingIconClicked = onSelectTimeTrailingIconClicked,

                    )

                Spacer(
                    Modifier.height(dateBottomSheetBottomSpacing())
                )
            }
        }
    }

}
