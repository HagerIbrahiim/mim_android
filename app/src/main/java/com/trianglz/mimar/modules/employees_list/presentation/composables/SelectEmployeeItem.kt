package com.trianglz.mimar.modules.employees_list.presentation.composables

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeMedium
import com.trianglz.mimar.modules.employee.presentation.model.EmployeeUIModel


@Composable
fun SelectEmployeeItem(employee: () -> EmployeeUIModel) {

    val isChecked = remember(employee().isChecked.value) {
        employee().isChecked.value
    }
    val checkboxIcon = remember(isChecked) {
        if (isChecked) R.drawable.radio_button_selected else R.drawable.radio_button_unselected
    }

    val checkedItemColor  : @Composable () -> Color = remember(isChecked) {
        {
            if (isChecked) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
        }
    }

    val onCheckboxBtnClicked : () -> Unit= remember (employee()){
        {
            employee().id?.let { employee().onClick(it) }
        }
    }

    val employeeNameFontWeight = remember(isChecked) {
        if(isChecked) FontWeight.W500 else FontWeight.W400
    }

    val nameEndPadding: @Composable () -> Dp = remember {
        { MaterialTheme.dimens.innerPaddingLarge.minus(2.dp) }
    }

    val enableEmployeeItemClick = remember(employee().showShimmer , isChecked, employee().isAvailable) {
        !employee().showShimmer && !isChecked && employee().isAvailable == true
    }

    val spaceBetweenTitleAndDesc : @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.spaceBetweenItemsSmall / 2
        }
    }

    val employeeUnAvailableAlpha = remember(employee().isAvailable) {
        if (employee().isAvailable == false) 0.5f else 1f
    }
    Row(modifier = Modifier
        .clickable(enabled = enableEmployeeItemClick, onClick = onCheckboxBtnClicked)
        .alpha(employeeUnAvailableAlpha)
        .fillMaxWidth()
        .padding(
            vertical = MaterialTheme.dimens.spaceBetweenItemsMedium,
            horizontal = MaterialTheme.dimens.screenGuideDefault
        ),
        verticalAlignment = Alignment.CenterVertically) {

        EmployeeImageWithCheckMark(employee)

        Column(modifier = Modifier
            .weight(1F)
            .padding(end = nameEndPadding())) {

            Text(
                text = employee().userName,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.body1.copy(
                    color = checkedItemColor(),
                    fontWeight = employeeNameFontWeight
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .shimmer(
                        employee().showShimmer,
                        shimmerWidth = 0.5f,
                    )
            )

                Spacer(modifier = Modifier.height(spaceBetweenTitleAndDesc()))

                Text(
                    text = employee().availabilityText.getString(LocalContext.current),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.caption,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .padding()
                        .shimmer(employee().showShimmer, .4f)
                )

        }

        if(employee().isAvailable == true) {
            IconButton(
                onClick = onCheckboxBtnClicked,
                enabled = !employee().showShimmer,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .size(MaterialTheme.dimens.iconSizeMedium)
                    .shimmer(employee().showShimmer,)
            ) {

                ImageFromRes(
                    imageId = checkboxIcon,
                    tintColor = checkedItemColor(),
                    modifier = Modifier
                )
            }
        }
    }







}