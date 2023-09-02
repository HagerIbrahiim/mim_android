package com.trianglz.mimar.modules.ratings.di

import com.trianglz.mimar.modules.ratings.data.data_source.RatingsRemoteDataSource
import com.trianglz.mimar.modules.ratings.data.data_source.RatingsRemoteDataSourceImpl
import com.trianglz.mimar.modules.ratings.data.repository.RatingRepositoryImpl
import com.trianglz.mimar.modules.ratings.data.retrofit.service.RatingsService
import com.trianglz.mimar.modules.ratings.domain.repository.RatingsRepository
import com.trianglz.mimar.modules.sign_in.data.retrofit.service.SignInService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class RatingsModule {


    @ViewModelScoped
    @Binds
    abstract fun bindRatingsRepository(ratingRepositoryImpl: RatingRepositoryImpl): RatingsRepository

    @ViewModelScoped
    @Binds
    abstract fun bindRatingsRemoteDataSource(ratingsRemoteDataSourceImpl: RatingsRemoteDataSourceImpl): RatingsRemoteDataSource

    companion object {
        @ViewModelScoped
        @Provides
        fun provideRatingService(retrofit: Retrofit): RatingsService =
            retrofit.create(RatingsService::class.java)
    }

}