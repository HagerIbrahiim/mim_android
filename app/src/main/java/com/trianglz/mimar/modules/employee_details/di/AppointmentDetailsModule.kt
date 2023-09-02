package com.trianglz.mimar.modules.employee_details.di

import com.trianglz.mimar.modules.employee_details.data.data_source.EmployeeDetailsRemoteDataSource
import com.trianglz.mimar.modules.employee_details.data.data_source.EmployeeDetailsRemoteDataSourceImpl
import com.trianglz.mimar.modules.employee_details.data.repository.EmployeeDetailsRepositoryImpl
import com.trianglz.mimar.modules.employee_details.data.retrofit.service.EmployeeDetailsService
import com.trianglz.mimar.modules.employee_details.domain.repository.EmployeeDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class EmployeeDetailsModule {

    @ViewModelScoped
    @Binds
    abstract fun bindEmployeesRemoteDataSource(EmployeesRemoteDataSourceImpl: EmployeeDetailsRemoteDataSourceImpl): EmployeeDetailsRemoteDataSource

    @ViewModelScoped
    @Binds
    abstract fun bindEmployeesRepository(EmployeeDetailsRepositoryImpl: EmployeeDetailsRepositoryImpl): EmployeeDetailsRepository

    companion object {
        @ViewModelScoped
        @Provides
        fun provideEmployeesService(retrofit: Retrofit): EmployeeDetailsService =
            retrofit.create(EmployeeDetailsService::class.java)
    }
}