package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.branch_view_all.presentation.composables.TitleWithIcons

@Composable
fun ExpandableCard(
    title: () -> StringWrapper,
    icon: @Composable () -> ImageVector,
    modifier: Modifier = Modifier,
    isLoading: () -> Boolean = { false },
    includeVerticalSpacingInExpandedContent: () -> Boolean = { true },
    isExpanded: () -> MutableState<Boolean>,
    expandedContent: @Composable ColumnScope.() -> Unit,
    ) {
    val toggleIconRes by remember {
        derivedStateOf {
            if (isExpanded().value) R.drawable.ic_arrow_up
            else R.drawable.ic_arrow_down
        }
    }
    val onItemClicked = remember {
        {
            isExpanded().value = !isExpanded().value
        }
    }

    val expandContentBottomPadding : @Composable () -> Dp = remember(includeVerticalSpacingInExpandedContent()){
        {
            if (includeVerticalSpacingInExpandedContent()) MaterialTheme.dimens.innerPaddingLarge else 0.dp
        }
    }

    val expandContentItemsSpacing: @Composable () -> Dp =
        remember(includeVerticalSpacingInExpandedContent()) {
            { if (includeVerticalSpacingInExpandedContent()) MaterialTheme.dimens.innerPaddingXLarge else 0.dp }
        }

    Column(
        modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colors.surface)
    ) {
        TitleWithIcons(
            title = title,
            startIcon = icon(),
            toggleIconAtEnd = ImageVector.vectorResource(id = toggleIconRes),
            showShimmer = isLoading,
            onItemClicked = onItemClicked
        )

        AnimatedVisibility(visible = isExpanded().value) {
            Column(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.dimens.innerPaddingLarge)
                    .padding(bottom = expandContentBottomPadding()),
                verticalArrangement = Arrangement.spacedBy(expandContentItemsSpacing()),
            ) {
                expandedContent()
            }
        }
    }
}

