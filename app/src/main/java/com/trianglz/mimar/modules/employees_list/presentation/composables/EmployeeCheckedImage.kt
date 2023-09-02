package com.trianglz.mimar.modules.employees_list.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Visibility
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.ImageItem
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeMedium
import com.trianglz.mimar.common.presentation.ui.theme.personImageSize
import com.trianglz.mimar.modules.employee.presentation.model.EmployeeUIModel

@Composable
fun EmployeeImageWithCheckMark(employee: () -> EmployeeUIModel) {

    val isChecked = remember(employee().isChecked.value) {
        employee().isChecked.value
    }
    val staffImageBorder : @Composable () -> Modifier =  remember(isChecked) {
        {
            if (isChecked && !employee().showShimmer) Modifier
                .border(
                    width = MaterialTheme.dimens.borderMedium,
                    shape = CircleShape,
                    color = MaterialTheme.colors.secondary
                )
            else Modifier
        }
    }

    val checkMarkVisibility = remember(isChecked , !employee().showShimmer) {
        if(isChecked && !employee().showShimmer)
            Visibility.Visible
        else
            Visibility.Invisible
    }

    val checkMarkInnerPadding : @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.innerPaddingSmall / 2
        }
    }

    val imageEndPadding: @Composable () -> Dp = remember {
        { MaterialTheme.dimens.innerPaddingLarge.minus(2.dp) }
    }

    val imageInnerPadding: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.innerPaddingXSmall / 2
        }
    }

    val checkIconSize: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.iconSizeMedium / 2
        }
    }

    ConstraintLayout(
        Modifier
            .wrapContentSize()
            .clip(MaterialTheme.shapes.medium)
            .padding(end = imageEndPadding())
          ) {
        val (staffImage, checkMark) = createRefs()

        Box(modifier = Modifier
            .then(staffImageBorder())
            .padding(imageInnerPadding())
            .clip(CircleShape)
            .shimmer(employee().showShimmer)
            .constrainAs(staffImage) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }) {
            ImageItem(
                image = employee().image,
                placeholder = R.drawable.ic_anyone,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(MaterialTheme.dimens.personImageSize)
            )
        }



        Box(modifier = Modifier
            .clip(CircleShape)
            .border(
                width = MaterialTheme.dimens.borderMedium,
                shape = CircleShape,
                color = MaterialTheme.colors.surface
            )
            .background(MaterialTheme.colors.secondary)
            .padding(checkMarkInnerPadding())
            .clip(CircleShape)
            .constrainAs(checkMark) {
                linkTo(staffImage.top, staffImage.bottom, bias = 0F, bottomMargin = (-2).dp)
                linkTo(staffImage.start, staffImage.end, bias = 1F, endMargin = (-4).dp)
                visibility = checkMarkVisibility
            }) {
            ImageFromRes(
                imageId = R.drawable.ic_check_icon,
                modifier = Modifier.size(checkIconSize()),
                tintColor = MaterialTheme.colors.surface
            )
        }



    }

}