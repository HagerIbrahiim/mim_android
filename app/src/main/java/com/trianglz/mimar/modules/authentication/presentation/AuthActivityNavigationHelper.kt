package com.trianglz.mimar.modules.authentication.presentation

import android.app.Activity
import com.trianglz.core.presentation.extensions.toActivityAsNewTaskWithParams
import com.trianglz.mimar.modules.notification.domain.manager.ForegroundNotificationManager


fun Activity.openAuthenticationWithMode(mode: AuthenticationMode) {
    val list = listOf(
        Pair(AuthenticationActivity.SCREEN_MODE, mode),
        Pair(
            ForegroundNotificationManager.ACTION_ID,
            intent.getStringExtra(ForegroundNotificationManager.ACTION_ID)
        ),
        Pair(
            ForegroundNotificationManager.CLICK_ACTION,
            intent.getStringExtra(ForegroundNotificationManager.CLICK_ACTION)
        ),
    )
    toActivityAsNewTaskWithParams<AuthenticationActivity>(*list.toTypedArray())
}
