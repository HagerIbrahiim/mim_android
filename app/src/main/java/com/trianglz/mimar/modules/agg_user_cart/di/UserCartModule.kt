package com.trianglz.mimar.modules.agg_user_cart.di

import com.trianglz.mimar.modules.agg_user_cart.data.repository.UserCartRepositoryImpl
import com.trianglz.mimar.modules.agg_user_cart.domain.repository.UserCartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class UserCartModule {
    @ActivityRetainedScoped
    @Binds
    abstract fun bindUserCartRepository(userCartRepositoryImpl: UserCartRepositoryImpl): UserCartRepository

}