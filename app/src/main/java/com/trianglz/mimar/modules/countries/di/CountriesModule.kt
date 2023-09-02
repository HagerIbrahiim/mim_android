package com.trianglz.mimar.modules.countries.di



import com.trianglz.mimar.modules.countries.data.remote.CountriesRemoteDataSource
import com.trianglz.mimar.modules.countries.data.remote.CountriesRemoteDataSourceImpl
import com.trianglz.mimar.modules.countries.data.repository.CountriesRepositoryImpl
import com.trianglz.mimar.modules.countries.data.retrofit.service.CountriesApisService
import com.trianglz.mimar.modules.countries.domain.repository.CountriesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class CountriesModule {

    @ViewModelScoped
    @Binds
    abstract fun bindCountriesRepo(CountriesRepoImpl: CountriesRepositoryImpl): CountriesRepository

    @ViewModelScoped
    @Binds
    abstract fun bindsCountriesRemoteDataSource(
        CountriesDataSourceImpl: CountriesRemoteDataSourceImpl
    ): CountriesRemoteDataSource

    companion object {
        @ViewModelScoped
        @Provides
        fun provideCountriesApisService(retrofit: Retrofit): CountriesApisService =
            retrofit.create(CountriesApisService::class.java)
    }
}