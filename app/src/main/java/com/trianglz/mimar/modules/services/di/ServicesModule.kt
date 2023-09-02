package com.trianglz.mimar.modules.services.di


import com.trianglz.mimar.modules.services.data.remote.ServicesRemoteDataSource
import com.trianglz.mimar.modules.services.data.remote.ServicesRemoteDataSourceImpl
import com.trianglz.mimar.modules.services.data.repository.ServicesRepositoryImpl
import com.trianglz.mimar.modules.services.data.retrofit.service.BranchServicesService
import com.trianglz.mimar.modules.services.domain.repository.ServicesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServicesModule {

    @ViewModelScoped
    @Binds
    abstract fun bindServicesRemoteDataSource(ServicesRemoteDataSourceImpl: ServicesRemoteDataSourceImpl): ServicesRemoteDataSource


    @ViewModelScoped
    @Binds
    abstract fun bindServicesRepository(ServicesRepositoryImpl: ServicesRepositoryImpl): ServicesRepository

    companion object {
        @ViewModelScoped
        @Provides
        fun provideServicesService(retrofit: Retrofit): BranchServicesService =
            retrofit.create(BranchServicesService::class.java)
    }
}