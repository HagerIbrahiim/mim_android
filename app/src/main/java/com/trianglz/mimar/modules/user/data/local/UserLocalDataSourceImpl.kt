package com.trianglz.mimar.modules.user.data.local

import com.trianglz.mimar.data_store.MimarDataStore
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

/**
 * Created by hassankhamis on 25,July,2022
 */
class UserLocalDataSourceImpl @Inject constructor(
    private val dataStore: MimarDataStore
): UserLocalDataSource {
    override suspend fun getUser(): UserDomainModel {
        return dataStore.getUser()
    }

    var userFlow: Flow<UserDomainModel>? = null

    override fun getUserUpdates(): Flow<UserDomainModel> {
        if (userFlow == null) {
            userFlow = dataStore.getUserFlow().filterNotNull()
        }
        return userFlow!!
    }

    override suspend fun setUser(user: UserDomainModel, isSetAuthToken: Boolean) {
        dataStore.setUser(user)
        if (isSetAuthToken) {
            if (!user.authToken.isNullOrEmpty()) {
                dataStore.setAuthToken(user.authToken)
            }
            dataStore.setIsLoggedIn(true)
        }
    }

    override suspend fun clearUser() {
        dataStore.clearUser()
    }

    override fun getAuthToken(): String {
        return dataStore.authToken
    }

    override  fun checkIfUserLoggedIn(): Boolean {
        return dataStore.isUserLoggedIn
    }

    override suspend fun setOnBoardingViewed(isOnBoardingViewed: Boolean) {
        dataStore.setIsOnBoardingViewed(isOnBoardingViewed)
    }

    override suspend fun checkIfOnBoardingViewed(): Boolean {
        return dataStore.getIsOnBoardingViewed()
    }

}
