package com.trianglz.mimar.modules.payment.di

import com.trianglz.mimar.modules.payment.data.repository.BasePaymentRepoImpl
import com.trianglz.mimar.modules.payment.domain.repository.BasePaymentRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class BasePaymentModule {
    @Binds
    @ActivityRetainedScoped
    abstract fun bindsBasePaymentRepo(basePaymentRepoImpl: BasePaymentRepoImpl): BasePaymentRepo
}