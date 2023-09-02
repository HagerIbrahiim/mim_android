package com.trianglz.mimar.modules.change_password.di

import com.trianglz.mimar.modules.change_password.data.remote.ChangePasswordRemoteDataSource
import com.trianglz.mimar.modules.change_password.data.remote.ChangePasswordRemoteDataSourceImpl
import com.trianglz.mimar.modules.change_password.data.repository.ChangePasswordRepoImpl
import com.trianglz.mimar.modules.change_password.data.retrofit.service.ChangePasswordService
import com.trianglz.mimar.modules.change_password.domain.repository.ChangePasswordRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class ChangePasswordModule {

    @ViewModelScoped
    @Binds
    abstract fun bindChangePasswordRemoteDataSource(ChangePasswordRemoteDataSourceImpl: ChangePasswordRemoteDataSourceImpl): ChangePasswordRemoteDataSource


    @ViewModelScoped
    @Binds
    abstract fun bindChangePasswordRepository(ChangePasswordRepoImpl: ChangePasswordRepoImpl): ChangePasswordRepo

    companion object {
        @ViewModelScoped
        @Provides
        fun provideChangePasswordService(retrofit: Retrofit): ChangePasswordService =
            retrofit.create(ChangePasswordService::class.java)
    }
}
