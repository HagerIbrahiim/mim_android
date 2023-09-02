package com.trianglz.mimar.common.presentation.extensions

import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.Direction
import com.trianglz.mimar.modules.NavGraphs

fun NavHostController.navigateToBottomNavScreen(route: String,){
    this.navigate(route) {
        // Pop up to the root of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(NavGraphs.mainGraph) {
            saveState = true
        }

        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}