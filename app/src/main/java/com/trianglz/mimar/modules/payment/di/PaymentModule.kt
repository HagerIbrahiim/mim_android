package com.trianglz.mimar.modules.payment.di

import com.trianglz.mimar.modules.payment.data.remote.data_source.PaymentRemoteDataSource
import com.trianglz.mimar.modules.payment.data.remote.data_source.PaymentRemoteDataSourceImpl
import com.trianglz.mimar.modules.payment.data.repository.PaymentRepoImpl
import com.trianglz.mimar.modules.payment.data.remote.retrofit.service.PaymentService
import com.trianglz.mimar.modules.payment.domain.repository.PaymentRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class PaymentModule {
    companion object {
        @ViewModelScoped
        @Provides
        fun providePaymentService(retrofit: Retrofit): PaymentService = retrofit.create(
            PaymentService::class.java)
    }
    @Binds
    @ViewModelScoped
    abstract fun bindsPaymentRepo(paymentRepoImpl: PaymentRepoImpl): PaymentRepo

    @Binds
    @ViewModelScoped
    abstract fun bindsPaymentRemoteDataSource(paymentRemoteDataSourceImpl: PaymentRemoteDataSourceImpl): PaymentRemoteDataSource
}