package com.trianglz.mimar.modules.specilaities.di


import com.trianglz.mimar.modules.specilaities.data.remote.SpecialtiesRemoteDataSource
import com.trianglz.mimar.modules.specilaities.data.remote.SpecialtiesRemoteDataSourceImpl
import com.trianglz.mimar.modules.specilaities.data.repository.SpecialtiesRepositoryImpl
import com.trianglz.mimar.modules.specilaities.data.retrofit.service.SpecialtiesService
import com.trianglz.mimar.modules.specilaities.domain.repository.SpecialtiesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class SpecialtiesModule {

    @Binds
    abstract fun bindSpecialtiesRemoteDataSource(specialtiesRemoteDataSourceImpl: SpecialtiesRemoteDataSourceImpl): SpecialtiesRemoteDataSource


    @ViewModelScoped
    @Binds
    abstract fun bindSpecialtiesRepository(specialtiesRepositoryImpl: SpecialtiesRepositoryImpl): SpecialtiesRepository

    companion object {
        @ViewModelScoped
        @Provides
        fun provideSpecialtiesService(retrofit: Retrofit): SpecialtiesService =
            retrofit.create(SpecialtiesService::class.java)
    }
}