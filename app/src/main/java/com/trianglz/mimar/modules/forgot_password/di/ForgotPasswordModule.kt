package com.trianglz.mimar.modules.forgot_password.di

import com.trianglz.mimar.modules.forgot_password.data.remote.ForgotPasswordRemoteDataSource
import com.trianglz.mimar.modules.forgot_password.data.remote.ForgotPasswordRemoteDataSourceImpl
import com.trianglz.mimar.modules.forgot_password.data.repository.ForgotPasswordRepoImpl
import com.trianglz.mimar.modules.forgot_password.data.retrofit.service.ForgotPasswordService
import com.trianglz.mimar.modules.forgot_password.domain.repository.ForgotPasswordRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class ForgotPasswordModule {

    @ViewModelScoped
    @Binds
    abstract fun bindForgotPasswordRemoteDataSource(forgotPasswordRemoteDataSourceImpl: ForgotPasswordRemoteDataSourceImpl): ForgotPasswordRemoteDataSource


    @ViewModelScoped
    @Binds
    abstract fun bindForgotPasswordRepository(forgotPasswordRepoImpl: ForgotPasswordRepoImpl): ForgotPasswordRepo

    companion object {
        @ViewModelScoped
        @Provides
        fun provideForgotPasswordService(retrofit: Retrofit): ForgotPasswordService =
            retrofit.create(ForgotPasswordService::class.java)
    }
}
