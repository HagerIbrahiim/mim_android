package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeMedium


@Composable
fun BackButtonCompose(
    modifier: Modifier = Modifier,
    showShimmer: () -> Boolean = { false },
    tintColor: Color = MaterialTheme.colors.primary,
    onclick: () -> Unit
) {
    val clickAction = remember {
        {
            onclick
        }
    }
    IconButton(
        modifier = modifier, onClick = clickAction(), enabled = !showShimmer()
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .size(MaterialTheme.dimens.iconSizeMedium)
                .shimmer(showShimmer()),
            tint = tintColor

        )
    }
}