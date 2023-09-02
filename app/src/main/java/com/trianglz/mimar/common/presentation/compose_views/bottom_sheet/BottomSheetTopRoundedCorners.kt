package com.trianglz.mimar.common.presentation.compose_views.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.setStatusBarPadding
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.common.presentation.ui.theme.topRoundedCornerShapeLarge

@Composable
fun BottomSheetTopRoundedCorners(
    containerModifier: @Composable () -> Modifier = { Modifier.padding(bottom = MaterialTheme.dimens.bottomNavigationHeight + MaterialTheme.dimens.screenGuideDefault) },
    containerBackgroundColor: @Composable () -> Color = { MaterialTheme.colors.surface },
    content: @Composable (BoxScope.() -> Unit)
) {

    Box(
        modifier = Modifier
            .setStatusBarPadding()
           // .calculateBottomPadding()
            .clip(MaterialTheme.shapes.topRoundedCornerShapeLarge)
            .background(containerBackgroundColor())
            .then(containerModifier())
    ) {

        content()
    }

}