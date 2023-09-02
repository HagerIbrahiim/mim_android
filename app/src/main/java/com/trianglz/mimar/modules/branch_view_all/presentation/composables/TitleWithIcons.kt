package com.trianglz.mimar.modules.branch_view_all.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeMedium

@Composable
fun TitleWithIcons(
    title: () -> StringWrapper,
    startIcon: ImageVector,
    toggleIconAtEnd: ImageVector? = null,
    modifier: Modifier = Modifier,
    showShimmer: () -> Boolean = { false },
    onItemClicked: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val newModifier by remember {
        derivedStateOf {
            if (onItemClicked != null)
                modifier.clickable(enabled = !showShimmer(), onClick = onItemClicked)
            else
                modifier
        }
    }
    val includeIconAtEnd by remember {
        derivedStateOf {
            toggleIconAtEnd != null
        }
    }

    Row(
        modifier = newModifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.innerPaddingLarge),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.innerPaddingMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(MaterialTheme.dimens.iconSizeMedium)
                .shimmer(showShimmer()),
            imageVector = startIcon,
            tint = MaterialTheme.colors.secondary,
            contentDescription = null
        )
        Text(
            modifier = Modifier.weight(1f).shimmer(showShimmer()),
            text = title().getString(context),
            style = MaterialTheme.typography.subtitle2.copy(
                fontWeight = FontWeight.W600
            ),
            textAlign = TextAlign.Start,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        if (includeIconAtEnd)
            toggleIconAtEnd?.let {
                Icon(
                    modifier = Modifier.size(MaterialTheme.dimens.iconSizeMedium).shimmer(showShimmer()),
                    imageVector = it,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = null
                )
            }
    }
}