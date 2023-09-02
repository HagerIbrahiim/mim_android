package com.trianglz.mimar.modules.notification.di

import com.trianglz.core.di.qualifiers.NotificationsScopeQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class BaseNotificationsModule {

    companion object {
        @Provides
        @ActivityRetainedScoped
        @NotificationsScopeQualifier
        fun providesNotificationScope(): CoroutineScope {
            return CoroutineScope(Dispatchers.IO)
        }
    }

}