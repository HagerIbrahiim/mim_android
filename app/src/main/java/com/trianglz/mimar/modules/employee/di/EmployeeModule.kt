package com.trianglz.mimar.modules.employee.di


import com.trianglz.mimar.modules.employee.data.remote.EmployeeRemoteDataSource
import com.trianglz.mimar.modules.employee.data.remote.EmployeeRemoteDataSourceImpl
import com.trianglz.mimar.modules.employee.data.repository.EmployeeRepositoryImpl
import com.trianglz.mimar.modules.employee.data.retrofit.service.EmployeeService
import com.trianglz.mimar.modules.employee.domain.repository.EmployeeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class EmployeeModule {

    @ViewModelScoped
    @Binds
    abstract fun bindEmployeeRemoteDataSource(EmployeeRemoteDataSourceImpl: EmployeeRemoteDataSourceImpl): EmployeeRemoteDataSource


    @ViewModelScoped
    @Binds
    abstract fun bindEmployeeRepository(EmployeeRepoImpl: EmployeeRepositoryImpl): EmployeeRepository

    companion object {
        @ViewModelScoped
        @Provides
        fun provideEmployeeService(retrofit: Retrofit): EmployeeService =
            retrofit.create(EmployeeService::class.java)
    }
}
