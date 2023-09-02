package com.trianglz.mimar.modules.sign_up.di

import com.trianglz.mimar.modules.sign_up.data.remote.SignUpRemoteDataSource
import com.trianglz.mimar.modules.sign_up.data.remote.SignUpRemoteDataSourceImpl
import com.trianglz.mimar.modules.sign_up.data.repository.SignUpRepoImpl
import com.trianglz.mimar.modules.sign_up.data.retrofit.service.SignUpService
import com.trianglz.mimar.modules.sign_up.domain.repository.SignUpRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class SignUpModule {

    @ViewModelScoped
    @Binds
    abstract fun bindSignUpRemoteDataSource(SignUpRemoteDataSourceImpl: SignUpRemoteDataSourceImpl): SignUpRemoteDataSource


    @ViewModelScoped
    @Binds
    abstract fun bindSignUpRepository(SignUpRepoImpl: SignUpRepoImpl): SignUpRepo

    companion object {
        @ViewModelScoped
        @Provides
        fun provideSignUpService(retrofit: Retrofit): SignUpService =
            retrofit.create(SignUpService::class.java)
    }
}
