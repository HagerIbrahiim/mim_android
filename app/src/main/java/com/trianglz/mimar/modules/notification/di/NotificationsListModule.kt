package com.trianglz.mimar.modules.notification.di

import com.trianglz.mimar.modules.notification.data.remote.NotificationRemoteDataSource
import com.trianglz.mimar.modules.notification.data.remote.NotificationRemoteDataSourceImpl
import com.trianglz.mimar.modules.notification.data.repository.NotificationRepositoryImpl
import com.trianglz.mimar.modules.notification.data.retrofit.service.NotificationService
import com.trianglz.mimar.modules.notification.domain.repository.NotificationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class NotificationsListModule {

    @ViewModelScoped
    @Binds
    abstract fun bindNotificationRemoteDataSource(notificationRemoteDataSourceImpl: NotificationRemoteDataSourceImpl): NotificationRemoteDataSource


    @ViewModelScoped
    @Binds
    abstract fun bindNotificationRepository(notificationRepoImpl: NotificationRepositoryImpl): NotificationRepository

    companion object {
        @ViewModelScoped
        @Provides
        fun provideNotificationsListService(retrofit: Retrofit): NotificationService =
            retrofit.create(NotificationService::class.java)
    }
}