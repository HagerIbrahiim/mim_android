package com.trianglz.mimar.di

import com.trianglz.mimar.common.presentation.compose_views.MimarComposeUIComponentsImpl
import androidx.compose.material.Shapes
import com.trianglz.core.data.retrofit.interceptor.AuthenticationInterceptor
import com.trianglz.core.data.retrofit.interceptor.GeneralInterceptor
import com.trianglz.core.di.qualifiers.BaseUrl
import com.trianglz.core.di.qualifiers.GoogleClientId
import com.trianglz.core.domain.model.GlobalCoroutineScopeHolder
import com.trianglz.core.presentation.model.ApplicationLifeCycleScope
import com.trianglz.core_compose.di.qualifiers.*
import com.trianglz.core_compose.presentation.compose_ui.BaseColors
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens
import com.trianglz.mimar.BuildConfig
import com.trianglz.mimar.common.data.interceptor.AuthenticationInterceptorImpl
import com.trianglz.mimar.common.data.interceptor.GeneralInterceptorImpl
import com.trianglz.mimar.common.presentation.ui.theme.MimarDimensImpl
import com.trianglz.mimar.common.presentation.ui.theme.MimarLightColors
import com.trianglz.mimar.common.presentation.ui.theme.Shapes
import com.trianglz.mimar.common.presentation.ui.theme.Typography
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MimarModule {
    companion object {
        @Provides
        @Singleton
        @BaseUrl
        fun addBaseUrl(): String {
            return BuildConfig.BASE_URL
        }

        @Provides
        @Singleton
        @IntoSet
        @GoogleClientId
        fun addGoogleClientId(): String {
            return BuildConfig.GOOGLE_AUTH_CLIENT_ID
        }
        @Singleton
        @Provides
        @ComposeShapes
        fun provideShapes(): Shapes = Shapes

        @Singleton
        @Provides
        @ComposeTypography
        fun provideTypography(): androidx.compose.material.Typography = Typography

        @Singleton
        @Provides
        @IntoSet
        fun provideMainComponents(): BaseComposeMainUIComponents {
            return MimarComposeUIComponentsImpl()
        }

        @Singleton
        @Provides
        fun provideGlobalCoroutineScopeHolder(): GlobalCoroutineScopeHolder {
            return ApplicationLifeCycleScope()
        }

    }
    @Singleton
    @Binds
    abstract fun provideAuthenticationInterceptor(authenticationInterceptorImpl: AuthenticationInterceptorImpl): AuthenticationInterceptor

    @Singleton
    @Binds
    abstract fun provideGeneralInterceptor(generalInterceptorImpl: GeneralInterceptorImpl): GeneralInterceptor

    @Singleton
    @Binds
    @LightMode
    abstract fun provideLightColors(colors: MimarLightColors): BaseColors

    @Singleton
    @Binds
    @DarkMode
    abstract fun provideDarkColors(colors: MimarLightColors): BaseColors

    @Singleton
    @Binds
    @ComposeDimens
    @IntoSet
    abstract fun provideMimarDimens(dimens: MimarDimensImpl): BaseDimens

}