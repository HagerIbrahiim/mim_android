package com.trianglz.mimar.modules.cart.di

import com.trianglz.mimar.modules.cart.data.remote.model.CartDataModel
import com.trianglz.mimar.modules.cart.data.remote.data_source.CartRemoteDataSource
import com.trianglz.mimar.modules.cart.data.remote.data_source.CartRemoteDataSourceImpl
import com.trianglz.mimar.modules.cart.data.remote.retrofit.service.CartService
import com.trianglz.mimar.modules.cart.data.repository.CartRepositoryImpl
import com.trianglz.mimar.modules.cart.di.qualifiers.CartSocketServiceQualifier
import com.trianglz.mimar.modules.cart.domain.repository.CartRepository
import com.trianglz.mimar.modules.socket.data.remote.service.SocketService
import com.trianglz.mimar.modules.socket.data.remote.service.SocketServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class CartModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindCartRemoteDataSource(cartRemoteDataSourceImpl: CartRemoteDataSourceImpl): CartRemoteDataSource


    @ActivityRetainedScoped
    @Binds
    abstract fun bindCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository

    @ActivityRetainedScoped
    @Binds
    @CartSocketServiceQualifier
    abstract fun bindsSocketService(socketServiceImpl: SocketServiceImpl<CartDataModel>): SocketService<CartDataModel>

    companion object {
        @ActivityRetainedScoped
        @Provides
        fun provideCartService(retrofit: Retrofit): CartService =
            retrofit.create(CartService::class.java)
    }
}