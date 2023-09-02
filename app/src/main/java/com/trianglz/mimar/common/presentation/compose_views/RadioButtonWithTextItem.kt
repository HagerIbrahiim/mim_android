package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.selectables.model.SelectableWithIconUIModel
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeXSmall

@Composable
fun RadioButtonWithTextItem(
    modifier: Modifier = Modifier,
    item: SelectableWithIconUIModel,
    onCheckedChanged: (SelectableWithIconUIModel) -> Unit,
) {

    val context = LocalContext.current

    val isChecked by remember(item.isChecked) {
        item.isChecked
    }

    val checkedIcon = remember(isChecked) {
        if (isChecked) R.drawable.radio_button_selected else R.drawable.radio_button_unselected
    }

    val itemColor: @Composable () -> Color = remember(isChecked) {
        { if (isChecked) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
//            .clip(MaterialTheme.shapes.medium)
            .clickable(enabled = !item.showShimmer) {
                onCheckedChanged(item)
            }
            .padding(start = MaterialTheme.dimens.screenGuideDefault),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(MaterialTheme.dimens.iconSizeXSmall)
                .shimmer(item.showShimmer),
            painter = painterResource(id = item.icon),
            contentDescription = null,
            tint = itemColor()
        )

        Text(
            text = item.title.getString(context),
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.body1.copy(color = itemColor()),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = MaterialTheme.dimens.spaceBetweenItemsSmall)
                .shimmer(item.showShimmer)
        )

        IconToggleButton(
            checked = isChecked,
            onCheckedChange = { onCheckedChanged(item) }) {
            Icon(
                modifier = Modifier
                    .size(MaterialTheme.dimens.iconSizeXSmall)
                    .shimmer(item.showShimmer),
                painter = painterResource(id = checkedIcon),
                contentDescription = null,
                tint = itemColor()
            )
        }
    }
}
