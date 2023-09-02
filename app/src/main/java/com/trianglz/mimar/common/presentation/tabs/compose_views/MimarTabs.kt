package com.trianglz.mimar.common.presentation.tabs.compose_views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.zIndex
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.common.presentation.extensions.ifNotNull
import com.trianglz.mimar.common.presentation.ui.theme.tabRowHeight
import com.trianglz.mimar.common.presentation.ui.theme.xxSmall
import com.trianglz.mimar.common.presentation.tabs.models.TabItemUIModel

@Composable
fun MimarTabs(
    modifier: @Composable () -> Modifier = { Modifier },
    indicatorBackground: @Composable () -> Brush? = { null },
    tabsBackground: @Composable () -> Color = { MaterialTheme.colors.surface },
    list: () -> SnapshotStateList<TabItemUIModel>,
//    showProviders: () -> Boolean,
    onTabChanged: (Int) -> Unit,
) {

    val tabHeight: @Composable () -> Dp = remember {
        { MaterialTheme.dimens.tabRowHeight }
    }

//    val selectedPage = remember(showProviders()) {
//        if (showProviders()) 0 else 1
//    }

    val selectedTabIndex by remember {
        derivedStateOf {
            list().indexOfFirst { it.isSelected.value }
        }
    }


    val searchTabSpacing: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.innerPaddingXSmall / 2
        }
    }

    val shadowElevation: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.cardElevation * 2
        }
    }


    Box(
        modifier = Modifier
            .then(modifier())
            .clip(MaterialTheme.shapes.xxSmall)
    ) {

        TabRow(
            selectedTabIndex = selectedTabIndex,
            backgroundColor = tabsBackground(),
            modifier = Modifier.wrapContentHeight(),
            indicator = { tabPositions: List<TabPosition> ->
                tabPositions.getOrNull(selectedTabIndex)?.let {
                    Box(
                        Modifier
                            .tabIndicatorOffset(it)
                            .requiredHeight(tabHeight())
                            .background(
                                Color.Transparent,
                                shape = MaterialTheme.shapes.xxSmall
                            )

                            .padding(
                                all = searchTabSpacing()
                            )
                            .shadow(
                                shadowElevation(),
                                shape = MaterialTheme.shapes.xxSmall,
                            )
                            .background(
                                MaterialTheme.colors.secondary,
                                shape = MaterialTheme.shapes.xxSmall
                            )
                            .ifNotNull(indicatorBackground()) {brush ->
                                Modifier.background(
                                    brush,
                                    shape = MaterialTheme.shapes.xxSmall
                                )
                            }
                            .zIndex(-1F)
                    ) {}
                }

            },
            // Divider is added here to remove redundant elevation from TabRow
            divider = {},
        ) {
            list().forEachIndexed { index, item ->
                MimarTabItem(
                    tabHeight = tabHeight,
                    tab = { item },
//                    selected = { selectedPage == index }
                ) {
                    onTabChanged(index)
                }
            }
        }
    }

}