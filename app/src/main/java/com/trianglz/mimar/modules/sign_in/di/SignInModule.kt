package com.trianglz.mimar.modules.sign_in.di

import com.trianglz.mimar.modules.sign_in.data.remote.SignInRemoteDataSource
import com.trianglz.mimar.modules.sign_in.data.remote.SignInRemoteDataSourceImpl
import com.trianglz.mimar.modules.sign_in.data.repository.SignInRepoImpl
import com.trianglz.mimar.modules.sign_in.data.retrofit.service.SignInService
import com.trianglz.mimar.modules.sign_in.domain.repository.SignInRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class SignInModule {

    @ViewModelScoped
    @Binds
    abstract fun bindSignInRemoteDataSource(SignInRemoteDataSourceImpl: SignInRemoteDataSourceImpl): SignInRemoteDataSource


    @ViewModelScoped
    @Binds
    abstract fun bindSignInRepository(SignInRepoImpl: SignInRepoImpl): SignInRepo

    companion object {
        @ViewModelScoped
        @Provides
        fun provideSignInService(retrofit: Retrofit): SignInService =
            retrofit.create(SignInService::class.java)
    }
}
