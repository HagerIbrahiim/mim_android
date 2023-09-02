package com.trianglz.mimar.modules.socket.di

import com.trianglz.mimar.BuildConfig
import com.trianglz.mimar.modules.socket.di.qualifiers.SocketScopeQualifier
import com.trianglz.mimar.modules.socket.di.qualifiers.SocketUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class SocketModule {

    companion object {
        @Provides
        @ActivityRetainedScoped
        @SocketScopeQualifier
        fun providesSocketScope(): CoroutineScope {
            return CoroutineScope(Dispatchers.IO)
        }
        @Provides
        @ActivityRetainedScoped
        @SocketUrl
        fun providesSocketUrl(): String {
            return BuildConfig.SOCKET_URL
        }

//        @Singleton
//        @Provides
//        fun provideWebSocketRetrofit(httpClient: OkHttpClient, builder: Retrofit.Builder, moshi: Moshi): Retrofit =
//            builder
//                .baseUrl(BuildConfig.SOCKET_URL)
//                .client(httpClient)
//                .build()
    }

}