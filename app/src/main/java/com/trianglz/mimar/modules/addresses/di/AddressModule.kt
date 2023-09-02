package com.trianglz.mimar.modules.addresses.di



import com.trianglz.mimar.modules.addresses.data.remote.AddressRemoteDataSource
import com.trianglz.mimar.modules.addresses.data.remote.AddressRemoteDataSourceImpl
import com.trianglz.mimar.modules.addresses.data.retrofit.service.AddressApisService
import com.trianglz.mimar.modules.addresses.data.repository.AddressRepoImpl
import com.trianglz.mimar.modules.addresses.domain.repository.AddressRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class AddressModule {
    @ViewModelScoped
    @Binds
    abstract fun bindAddressRepo(addressRepoImpl: AddressRepoImpl): AddressRepository

    @ViewModelScoped
    @Binds
    abstract fun bindsAddressRemoteDataSource(
        addressDataSourceImpl: AddressRemoteDataSourceImpl
    ): AddressRemoteDataSource

    companion object {
        @ViewModelScoped
        @Provides
        fun provideAddressApisService(retrofit: Retrofit): AddressApisService =
            retrofit.create(AddressApisService::class.java)
    }
}