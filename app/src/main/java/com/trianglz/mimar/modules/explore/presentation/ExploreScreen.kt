package com.trianglz.mimar.modules.explore.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core_compose.presentation.extensions.setStatusBarPadding
import com.trianglz.mimar.common.presentation.navigation.MainGraph

@MainGraph(start = false)
@Destination
@Composable
fun ExploreScreen(
    navigator: DestinationsNavigator
) {
    Box(modifier = Modifier.setStatusBarPadding()) {

        Text(text = "Explore Screen", color = MaterialTheme.colors.primary)
    }
}