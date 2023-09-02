package com.trianglz.mimar.common.presentation.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.common.presentation.ui.theme.dotSizeSmall


@Composable
fun BottomNavigationIconWithBadge(
    screen: () -> BottomBarDestinations,
    selected: () -> Boolean,
    showBadge: () -> Boolean
) {

    ConstraintLayout {

        val (icon, badge, dot) = createRefs()

        val topDotSpace = MaterialTheme.dimens.spaceBetweenItemsXSmall

        val image: @Composable () -> ImageVector = remember(selected) {
            {
                if (selected.invoke()) ImageVector.vectorResource(id = screen.invoke().selectedIconId) else ImageVector.vectorResource(
                    id = screen.invoke().unSelectedIconId
                )
            }
        }

        Icon(
            imageVector = image.invoke(),
            contentDescription = "Navigation Icon",
            modifier = Modifier.constrainAs(icon) {
                linkTo(parent.top, parent.bottom)
                linkTo(parent.start, parent.end)
            }
        )

        if (selected()) {
            Box(modifier = Modifier
                .constrainAs(dot) {
                    top.linkTo(icon.bottom, topDotSpace)
                    linkTo(parent.start, parent.end)
                }
                .size(MaterialTheme.dimens.dotSizeSmall)
                .clip(CircleShape)
                .background(MaterialTheme.colors.secondary))

        }

        if (showBadge.invoke()) {
            Spacer(modifier = Modifier
                .border(
                    border = BorderStroke(1.dp, MaterialTheme.colors.surface),
                    shape = CircleShape,
                )
                .size(10.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary)
                .constrainAs(badge) {
                    linkTo(parent.top, parent.bottom, bias = 0F, topMargin = 2.dp)
                    linkTo(parent.start, parent.end, bias = 1F)
                })
        }


    }
}