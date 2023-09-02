package com.trianglz.mimar.modules.filter.presenation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.bottom_sheet.BottomSheetRoundedContainerWithButton
import com.trianglz.mimar.common.presentation.compose_views.bottom_sheet.BottomSheetTopSection
import com.trianglz.mimar.modules.branch_reviews.presentation.composables.ReviewBar
import com.trianglz.mimar.modules.filter.presenation.model.BaseCheckboxItemUiModel
import com.trianglz.mimar.modules.filter.presenation.model.BaseSelectionUiModel
import com.trianglz.mimar.modules.filter.presenation.model.SelectionType
import com.trianglz.mimar.modules.ratings.presenation.model.RatingUIModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectionBottomSheet(
    selectionItem: () -> BaseSelectionUiModel?,
    isButtonEnabled: () -> Boolean = { true },
    buttonText: () -> Int? = { null },
    onSubmitBtnClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    onOptionChecked: (BaseCheckboxItemUiModel) -> Unit,
    onSelectAllSwitchStatusChanged: (isChecked: Boolean) -> Unit = {}
) {

    val customText: @Composable (BaseCheckboxItemUiModel) -> Unit? = remember {
        {
            if ( it is RatingUIModel) {
                Row {
                    ReviewBar(
                        showShimmer = { false },
                        rating = { it.rating.toInt() },
                    )

                    Spacer(modifier = Modifier.padding(end = MaterialTheme.dimens.innerPaddingMedium.plus(2.dp)))
                }

            }  else null
        }
    }

    val bottomSheetContentBottomPadding : @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.buttonHeight + MaterialTheme.dimens.spaceBetweenItemsXLarge
        }
    }

    val isListNotShimmering = remember(selectionItem()?.items, isButtonEnabled()) {
        selectionItem()?.items?.none { it.showShimmer } ?: true && isButtonEnabled()
    }

    BottomSheetRoundedContainerWithButton(
        isPrimaryButtonEnabled = { isListNotShimmering },
        primaryButtonText = buttonText,
        onPrimaryButtonClicked = onSubmitBtnClicked
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = bottomSheetContentBottomPadding()),
            modifier = Modifier.padding(bottom = MaterialTheme.dimens.spaceBetweenItemsSmall * 2),
        ) {
            stickyHeader {
                BottomSheetTopSection(
                    title = { selectionItem()?.title ?: StringWrapper() },
                    selectAll = { selectionItem()?.selectAll?.value },
                    topPadding = MaterialTheme.dimens.screenGuideLarge,
                    showSelectAllSwitch = { selectionItem()?.selectionType is SelectionType.MultiSelection && isListNotShimmering },
                    onSelectAllSwitchStatusChanged = onSelectAllSwitchStatusChanged,
                    onBackButtonClicked = onBackButtonClicked
                )
            }

            selectionItem()?.let {
                items(selectionItem()?.items ?: listOf(), key = {it.id}){ item ->
                    CheckableItemWithText(
                        selectionType = selectionItem()?.selectionType ?:SelectionType.MultiSelection ,
                        item = { item },
                        customContent = { customText(item) },
                        onCheckedChanged = { onOptionChecked(item) })
                }
            }
        }
    }


}
