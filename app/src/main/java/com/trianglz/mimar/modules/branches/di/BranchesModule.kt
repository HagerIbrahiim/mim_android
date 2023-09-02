package com.trianglz.mimar.modules.branches.di

import com.trianglz.mimar.modules.branches.data.remote.data_source.BranchesRemoteDataSource
import com.trianglz.mimar.modules.branches.data.remote.data_source.BranchesRemoteDataSourceImpl
import com.trianglz.mimar.modules.branches.data.remote.retrofit.service.BranchesService
import com.trianglz.mimar.modules.branches.data.repository.BranchesRepositoryImpl
import com.trianglz.mimar.modules.branches.domain.repository.BranchesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class BranchesModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindBranchesRemoteDataSource(branchesRemoteDataSourceImpl: BranchesRemoteDataSourceImpl): BranchesRemoteDataSource


    @ActivityRetainedScoped
    @Binds
    abstract fun bindBranchesRepository(branchesRepositoryImpl: BranchesRepositoryImpl): BranchesRepository

    companion object {
        @ActivityRetainedScoped
        @Provides
        fun provideBranchesService(retrofit: Retrofit): BranchesService =
            retrofit.create(BranchesService::class.java)
    }
}