package com.trianglz.mimar.modules.user.data.repository

import com.trianglz.mimar.modules.user.data.local.UserLocalDataSource
import com.trianglz.mimar.modules.user.data.mapper.toDomainModel
import com.trianglz.mimar.modules.user.data.remote.UserRemoteDataSource
import com.trianglz.mimar.modules.user.domain.mapper.toDataModel
import com.trianglz.mimar.modules.user.domain.model.UpdateUserDomainModel
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import com.trianglz.mimar.modules.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : UserRepository {
    override suspend fun getUser(): UserDomainModel {
        return userLocalDataSource.getUser()
    }

    override fun getUserUpdates(): Flow<UserDomainModel> {
        return userLocalDataSource.getUserUpdates()
    }

    override suspend fun updateUser(updateUserDomainModel: UpdateUserDomainModel) : UserDomainModel {
        val response = userRemoteDataSource.updateUser(
           updateUserDomainModel.toDataModel()
        )
        setUser(response.toDomainModel(), false)
        return response.toDomainModel()
    }


    override suspend fun setUser(user: UserDomainModel, isSetAuthToken: Boolean) {
        // put user into shared pref
        userLocalDataSource.setUser(user, isSetAuthToken)
    }

    override suspend fun clearUser(deviceId: String?) {
        deviceId?.let { userRemoteDataSource.clearUser(it) }
        userLocalDataSource.clearUser()
    }

    override suspend fun deleteUser() {
        userRemoteDataSource.deleteUser()
        userLocalDataSource.clearUser()
    }

    override suspend fun checkIfUserLoggedIn(): Boolean {
        return userLocalDataSource.checkIfUserLoggedIn()
    }

    override fun getAuthToken(): String {
        return userLocalDataSource.getAuthToken()
    }

    override suspend fun setOnBoardingViewed(isOnBoardingViewed: Boolean) {
         userLocalDataSource.setOnBoardingViewed(isOnBoardingViewed)
    }

    override suspend fun checkIfOnBoardingViewed(): Boolean {
        return userLocalDataSource.checkIfOnBoardingViewed()
    }
}
