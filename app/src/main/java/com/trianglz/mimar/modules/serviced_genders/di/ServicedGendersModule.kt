package com.trianglz.mimar.modules.serviced_genders.di

import com.trianglz.mimar.modules.serviced_genders.data.remote.ServicedGenderRemoteDataSource
import com.trianglz.mimar.modules.serviced_genders.data.remote.ServicedGenderRemoteDataSourceImpl
import com.trianglz.mimar.modules.serviced_genders.data.repository.ServicedGendersRepositoryImpl
import com.trianglz.mimar.modules.serviced_genders.data.retrofit.service.ServicedGendersService
import com.trianglz.mimar.modules.serviced_genders.domain.repository.ServicedGendersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServicedGendersModule {

    @ViewModelScoped
    @Binds
    abstract fun bindServicedGendersRemoteDataSource(ServicedGendersRemoteDataSourceImpl:
                                                         ServicedGenderRemoteDataSourceImpl ): ServicedGenderRemoteDataSource


    @ViewModelScoped
    @Binds
    abstract fun bindServicedGendersRepository(ServicedGendersRepositoryImpl: ServicedGendersRepositoryImpl): ServicedGendersRepository

    companion object {
        @ViewModelScoped
        @Provides
        fun provideServicedGendersService(retrofit: Retrofit): ServicedGendersService =
            retrofit.create(ServicedGendersService::class.java)
    }
}