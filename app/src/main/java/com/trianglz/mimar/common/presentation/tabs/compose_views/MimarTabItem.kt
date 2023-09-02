package com.trianglz.mimar.common.presentation.tabs.compose_views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.mimar.common.presentation.ui.theme.xxSmall
import com.trianglz.mimar.common.presentation.tabs.models.TabItemUIModel


@Composable
fun MimarTabItem(tab: ()-> TabItemUIModel, tabHeight: @Composable ()-> Dp, onClick: () -> Unit) {

    val context = LocalContext.current

    /**
     * Here I want view recomposes every change to avoid bad appearance in view when change tap so i didn't use
     *   val isSelected by remember {
     *       tab().isSelected
     *   }
     */

    val isSelected = tab().isSelected.value


    val tabContentColor : @Composable () -> Color = remember(isSelected) {
        { if (isSelected) MaterialTheme.colors.surface else
            MaterialTheme.colors.onBackground
        }
    }

    val searchTabHorizontalSpacing : @Composable () -> Dp  = remember {
        {
            MaterialTheme.dimens.innerPaddingXSmall /2
        }
    }

    val searchTabVerticalSpacing : @Composable () -> Dp  = remember {
        {
            MaterialTheme.dimens.innerPaddingXSmall / 4
        }
    }

    val innerPaddingXSmallHalf : @Composable () -> Dp  = remember {
        {
            MaterialTheme.dimens.innerPaddingXSmall / 2
        }
    }

    Tab(isSelected,
        enabled  = !isSelected,
        onClick = onClick,
        modifier = Modifier
            .requiredHeight(tabHeight())
            .padding(
                horizontal = searchTabHorizontalSpacing(),
                vertical = searchTabVerticalSpacing()
            )
            .clip(MaterialTheme.shapes.xxSmall)) {
        Row(
            Modifier
                .clip(MaterialTheme.shapes.xxSmall),
            horizontalArrangement = Arrangement.Center
        ) {
            //TODO use ImageFromRes after core updated
            Image(
                imageVector = ImageVector.vectorResource(id = tab().image),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .requiredHeight(16.dp),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(color = tabContentColor()),
            )

            Spacer(modifier = Modifier.padding(end = innerPaddingXSmallHalf()))

            Text(
                text = tab().title.getString(context),
                style = MaterialTheme.typography.body2.copy(color =tabContentColor(), fontWeight = FontWeight.W600),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}