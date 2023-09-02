package com.trianglz.mimar.modules.offered_location.data.di

import com.trianglz.mimar.modules.offered_location.data.remote.OfferedLocationRemoteDataSource
import com.trianglz.mimar.modules.offered_location.data.remote.OfferedLocationRemoteDataSourceImpl
import com.trianglz.mimar.modules.offered_location.data.repository.OfferedLocationsRepositoryImpl
import com.trianglz.mimar.modules.offered_location.data.retrofit.service.OfferedLocationService
import com.trianglz.mimar.modules.offered_location.domain.repository.OfferedLocationsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class OfferedLocationsModule {

    @ViewModelScoped
    @Binds
    abstract fun bindOfferedLocationsRemoteDataSource(offeredLocationRemoteDataSourceImpl: OfferedLocationRemoteDataSourceImpl): OfferedLocationRemoteDataSource


    @ViewModelScoped
    @Binds
    abstract fun bindOfferedLocationsRepository(OfferedLocationsRepositoryImpl: OfferedLocationsRepositoryImpl): OfferedLocationsRepository

    companion object {
        @ViewModelScoped
        @Provides
        fun provideOfferedLocationsService(retrofit: Retrofit): OfferedLocationService =
            retrofit.create(OfferedLocationService::class.java)
    }
}