package com.trianglz.mimar.modules.user.domain.repository

import com.trianglz.mimar.modules.user.domain.model.UpdateUserDomainModel
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import kotlinx.coroutines.flow.Flow

/**
 * The repository's main concept is observation. In other words the only read value function -> [getUser]
 * should return the latest user value in it's flow
 * and the rest should ( if succeeded ) overwrite the value saved on the system and triggers the
 * [getUser] flow
 */
interface UserRepository {
    suspend fun getUser(): UserDomainModel
    fun getUserUpdates(): Flow<UserDomainModel>
    /**
     * As we store the User, authentication token and isLoggedIn flag are stored separately. So, we
     * need to know if setting the user data is accompanied by setting the other data or not.
     * Hence, the @param isSetAuthToken
     */
    suspend fun setUser(user: UserDomainModel, isSetAuthToken: Boolean)
    suspend fun clearUser(deviceId: String?= null)
    suspend fun deleteUser()
    suspend fun updateUser(updateUserDomainModel: UpdateUserDomainModel):  UserDomainModel
    suspend fun checkIfUserLoggedIn(): Boolean
    fun getAuthToken(): String
    suspend fun setOnBoardingViewed(isOnBoardingViewed : Boolean)
    suspend fun checkIfOnBoardingViewed(): Boolean

}
