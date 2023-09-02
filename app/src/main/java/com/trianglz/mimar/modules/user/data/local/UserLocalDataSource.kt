package com.trianglz.mimar.modules.user.data.local

import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by hassankhamis on 25,July,2022
 */
interface UserLocalDataSource {
    suspend fun getUser(): UserDomainModel
    fun getUserUpdates(): Flow<UserDomainModel>
    /**
     * As we store the User, authentication token and isLoggedIn flag are stored separately. So, we
     * need to know if setting the user data is accompanied by setting the other data or not.
     * Hence, the @param isSetAuthToken
     */
    suspend fun setUser(user: UserDomainModel, isSetAuthToken: Boolean)
    suspend fun clearUser()
    fun getAuthToken(): String
    fun checkIfUserLoggedIn(): Boolean
    suspend fun setOnBoardingViewed(isOnBoardingViewed : Boolean)
    suspend fun checkIfOnBoardingViewed(): Boolean


}