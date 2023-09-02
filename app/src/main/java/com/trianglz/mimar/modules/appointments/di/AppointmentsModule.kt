package com.trianglz.mimar.modules.appointments.di

import com.trianglz.mimar.modules.appointments.data.remote.data_source.AppointmentsRemoteDataSource
import com.trianglz.mimar.modules.appointments.data.remote.data_source.AppointmentsRemoteDataSourceImpl
import com.trianglz.mimar.modules.appointments.data.remote.retrofit.service.AppointmentsService
import com.trianglz.mimar.modules.appointments.data.repository.AppointmentsRepositoryImpl
import com.trianglz.mimar.modules.appointments.domain.repository.AppointmentsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppointmentsModule {

    @ViewModelScoped
    @Binds
    abstract fun bindAppointmentsRemoteDataSource(appointmentsRemoteDataSourceImpl: AppointmentsRemoteDataSourceImpl): AppointmentsRemoteDataSource


    @ViewModelScoped
    @Binds
    abstract fun bindAppointmentsRepository(appointmentsRepositoryImpl: AppointmentsRepositoryImpl): AppointmentsRepository

    companion object {
        @ViewModelScoped
        @Provides
        fun provideAppointmentsService(retrofit: Retrofit): AppointmentsService =
            retrofit.create(AppointmentsService::class.java)
    }
}