package com.trianglz.mimar.modules.filter.presenation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeMedium
import com.trianglz.mimar.common.presentation.ui.theme.xSmall
import com.trianglz.mimar.modules.filter.presenation.model.BaseCheckboxItemUiModel
import com.trianglz.mimar.modules.filter.presenation.model.SelectionType

@Composable
fun CheckableItemWithText(
    item: () -> BaseCheckboxItemUiModel,
    selectionType: SelectionType,
    customContent: (@Composable () -> Unit)? = null,
    onCheckedChanged: (Boolean) -> Unit,
) {
    val context = LocalContext.current

    val isChecked = remember(item().isChecked.value) {
        item().isChecked.value
    }


    val checkedIcon = remember(isChecked, selectionType) {
        when (isChecked) {
            true -> {
                if (selectionType is SelectionType.MultiSelection)
                    R.drawable.ic_checkbox_checked
                else
                    R.drawable.radio_button_selected
            }
            false -> {
                if (selectionType is SelectionType.MultiSelection)
                    R.drawable.ic_checkbox_unchecked
                else
                    R.drawable.radio_button_unselected
            }
        }
    }


    val enableCheck = remember(selectionType, isChecked) {
        if(selectionType is SelectionType.SingleSelection)
            !item().isChecked.value
        else true
    }

    val text = remember( item()) {
       item().title
    }

    val textColor : @Composable () -> Color = remember(isChecked) {
        {
            if (isChecked) MaterialTheme.colors.primary
            else
                MaterialTheme.colors.onBackground
        }
    }

    val textAndIconSpacer : @Composable () -> Dp = remember() {
        {
            MaterialTheme.dimens.spaceBetweenItemsXSmall * 2
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.xSmall)
            .clickable( onClick = {
                onCheckedChanged(!isChecked)
            })
            .padding(
                horizontal = MaterialTheme.dimens.screenGuideDefault,
                vertical = MaterialTheme.dimens.spaceBetweenItemsMedium
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        ImageFromRes(
            modifier = Modifier
                .size(MaterialTheme.dimens.iconSizeMedium)
                .shimmer(item().showShimmer),
            imageId =  checkedIcon,
        )

        Spacer(modifier = Modifier.padding(start = textAndIconSpacer()))

        customContent?.let {
            it()
        }

        Text(
            text = text.getString(context).capitalize(),
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
           // maxLines = 1,
            style = MaterialTheme.typography.body1.copy(color = textColor(),),
            modifier = Modifier
                .weight(1f)
                .shimmer(item().showShimmer, 3F)

        )

    }
}