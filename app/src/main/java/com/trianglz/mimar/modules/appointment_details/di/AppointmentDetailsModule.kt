package com.trianglz.mimar.modules.appointment_details.di

import com.trianglz.mimar.modules.appointment_details.data.remote.data_source.AppointmentDetailsRemoteDataSource
import com.trianglz.mimar.modules.appointment_details.data.remote.data_source.AppointmentDetailsRemoteDataSourceImpl
import com.trianglz.mimar.modules.appointment_details.data.remote.retrofit.service.AppointmentDetailsService
import com.trianglz.mimar.modules.appointment_details.data.repository.AppointmentDetailsRepositoryImpl
import com.trianglz.mimar.modules.appointment_details.domain.repository.AppointmentDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppointmentDetailsModule {

    @ViewModelScoped
    @Binds
    abstract fun bindAppointmentsRemoteDataSource(appointmentsRemoteDataSourceImpl: AppointmentDetailsRemoteDataSourceImpl): AppointmentDetailsRemoteDataSource
    
    @ViewModelScoped
    @Binds
    abstract fun bindAppointmentsRepository(appointmentDetailsRepositoryImpl: AppointmentDetailsRepositoryImpl): AppointmentDetailsRepository

    companion object {
        @ViewModelScoped
        @Provides
        fun provideAppointmentsService(retrofit: Retrofit): AppointmentDetailsService =
            retrofit.create(AppointmentDetailsService::class.java)
    }
}