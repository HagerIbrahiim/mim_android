package com.trianglz.mimar.modules.verification.di

import com.trianglz.mimar.modules.verification.data.remote.VerificationRemoteDataSource
import com.trianglz.mimar.modules.verification.data.remote.VerificationRemoteDataSourceImpl
import com.trianglz.mimar.modules.verification.data.repository.VerificationRepoImpl
import com.trianglz.mimar.modules.verification.data.retrofit.service.VerificationService
import com.trianglz.mimar.modules.verification.domain.repository.VerificationRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class VerificationModule {

    @ViewModelScoped
    @Binds
    abstract fun bindVerificationRemoteDataSource(verificationRemoteDataSourceImpl: VerificationRemoteDataSourceImpl): VerificationRemoteDataSource


    @ViewModelScoped
    @Binds
    abstract fun bindVerificationRepository(VerificationRepoImpl: VerificationRepoImpl): VerificationRepo

    companion object {
        @ViewModelScoped
        @Provides
        fun provideVerificationService(retrofit: Retrofit): VerificationService =
            retrofit.create(VerificationService::class.java)
    }
}
