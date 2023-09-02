package com.trianglz.mimar.modules.user.di

import com.trianglz.mimar.modules.user.data.local.UserLocalDataSource
import com.trianglz.mimar.modules.user.data.local.UserLocalDataSourceImpl
import com.trianglz.mimar.modules.user.data.remote.UserRemoteDataSource
import com.trianglz.mimar.modules.user.data.remote.UserRemoteDataSourceImpl
import com.trianglz.mimar.modules.user.data.repository.UserRepositoryImpl
import com.trianglz.mimar.modules.user.data.retrofit.service.UserApisService
import com.trianglz.mimar.modules.user.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    @Singleton
    @Binds
    abstract fun bindsUserRepo(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindsUserRemoteDataSourceImpl(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindsUserLocalDataSourceImpl(userLocalDataSourceImpl: UserLocalDataSourceImpl): UserLocalDataSource

    companion object {
        @Singleton
        @Provides
        fun provideUserApisService(retrofit: Retrofit): UserApisService =
            retrofit.create(UserApisService::class.java)
    }
}