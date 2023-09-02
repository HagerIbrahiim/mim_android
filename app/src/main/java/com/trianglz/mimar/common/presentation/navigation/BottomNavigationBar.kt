package com.trianglz.mimar.common.presentation.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.utils.isRouteOnBackStack
import com.trianglz.core_compose.presentation.extensions.getCurrentDestinationRouteAsState
import com.trianglz.mimar.common.presentation.extensions.navigateToBottomNavScreen
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler

@Composable
fun BottomBar(navController: NavHostController, isReceiveChatNotification: Boolean,
              userModeHandler: UserModeHandler,) {
    val screens = BottomBarDestinations.values()

    val currentDestinationRoute = navController.getCurrentDestinationRouteAsState()

    BottomNavigation(backgroundColor = Color.White, modifier = Modifier
        .graphicsLayer {
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            clip = true
        }
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                navController = navController,
                selected = navController.isRouteOnBackStack(screen.direction),
                showChatBadge = false,
                userModeHandler = userModeHandler,
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarDestinations,
    navController: NavHostController,
    selected: Boolean,
    showChatBadge: Boolean,
    userModeHandler: UserModeHandler,
) {


    BottomNavigationItem(
        icon = {
            BottomNavigationIconWithBadge(
                { screen }, { selected }, { showChatBadge }
            )
        },
        selected = selected,
        selectedContentColor = Color.Unspecified,
        unselectedContentColor = Color.Unspecified,
        onClick = {
            if (selected) {
                // When we click again on a bottom bar item and it was already selected
                // we want to pop the back stack until the initial destination of this bottom bar item
                navController.popBackStack(screen.direction, false)
                return@BottomNavigationItem
            }
            val isGuest = userModeHandler.isGuestBlocking(false)
            if (screen.showToGuestUser || !isGuest) {
                navController.navigateToBottomNavScreen(screen.route)
            } else {
                userModeHandler.isGuestBlocking()

            }

        }
    )
}