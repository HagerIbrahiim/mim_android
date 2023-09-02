package com.trianglz.mimar.modules.categories.di

import com.trianglz.mimar.modules.categories.data.remote.data_source.CategoriesRemoteDataSource
import com.trianglz.mimar.modules.categories.data.remote.data_source.CategoriesRemoteDataSourceImpl
import com.trianglz.mimar.modules.categories.data.remote.retrofit.service.CategoriesService
import com.trianglz.mimar.modules.categories.data.repository.CategoriesRepositoryImpl
import com.trianglz.mimar.modules.categories.domain.repository.CategoriesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class CategoriesModule {

    @ViewModelScoped
    @Binds
    abstract fun bindCategoriesRemoteDataSource(categoriesRemoteDataSourceImpl: CategoriesRemoteDataSourceImpl): CategoriesRemoteDataSource


    @ViewModelScoped
    @Binds
    abstract fun bindCategoriesRepository(categoriesRepositoryImpl: CategoriesRepositoryImpl): CategoriesRepository

    companion object {
        @ViewModelScoped
        @Provides
        fun provideCategoriesService(retrofit: Retrofit): CategoriesService =
            retrofit.create(CategoriesService::class.java)
    }
}