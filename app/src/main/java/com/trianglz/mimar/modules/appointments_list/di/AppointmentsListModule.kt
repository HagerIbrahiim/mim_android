package com.trianglz.mimar.modules.appointments_list.di

import com.trianglz.mimar.modules.appointments_list.data.local.data_source.AppointmentListLocalDataSource
import com.trianglz.mimar.modules.appointments_list.data.local.data_source.AppointmentsListLocalDataSourceImpl
import com.trianglz.mimar.modules.appointments_list.data.remote.data_source.AppointmentsListRemoteDataSource
import com.trianglz.mimar.modules.appointments_list.data.remote.data_source.AppointmentsListRemoteDataSourceImpl
import com.trianglz.mimar.modules.appointments_list.data.remote.retrofit.service.AppointmentsListService
import com.trianglz.mimar.modules.appointments_list.data.repository.AppointmentsListListRepositoryImpl
import com.trianglz.mimar.modules.appointments_list.domain.repository.AppointmentsListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppointmentsListModule {

    @ViewModelScoped
    @Binds
    abstract fun bindAppointmentsRemoteDataSource(appointmentsListRemoteDataSourceImpl: AppointmentsListRemoteDataSourceImpl): AppointmentsListRemoteDataSource

    @ViewModelScoped
    @Binds
    abstract fun bindAppointmentsLocalDataSource(
        appointmentsListLocaleDataSourceImpl: AppointmentsListLocalDataSourceImpl
    ): AppointmentListLocalDataSource


    @ViewModelScoped
    @Binds
    abstract fun bindAppointmentsListRepository(appointmentsListRepositoryImpl: AppointmentsListListRepositoryImpl): AppointmentsListRepository

    companion object {
        @ViewModelScoped
        @Provides
        fun provideAppointmentsService(retrofit: Retrofit): AppointmentsListService =
            retrofit.create(AppointmentsListService::class.java)
    }
}