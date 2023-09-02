package com.trianglz.mimar.data_store

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.trianglz.core.R
import com.trianglz.core.di.qualifiers.DataStoreScopeQualifier
import com.trianglz.core.domain.exceptions.CustomException
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by hassankhamis on 25,July,2022
 */

@Singleton
class MimarDataStore @Inject constructor(private val context: Application, @DataStoreScopeQualifier scope: CoroutineScope) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "MY_MIMAR_PREF")
    private val userPref = stringPreferencesKey("user_pref")
    private val authTokenPref = stringPreferencesKey("auth_token")
    private val isLoggedInPref = booleanPreferencesKey("is_logged_in")
    private val isOnBoardingViewedPref = booleanPreferencesKey("is_on_boarding_viewed")

    var authToken: String = ""
    var isUserLoggedIn: Boolean = false

    init {
        scope.launch {
            authToken = getAuthToken()
            isUserLoggedIn = getIsLoggedIn()
        }
    }

    suspend fun setUser(userDomainModel: UserDomainModel) {
        context.dataStore.edit { settings ->
            settings[userPref] = userDomainModel.toString()
        }
    }

    fun getUserFlow(): Flow<UserDomainModel?> {
        return context.dataStore.data
            .catch {
                throw CustomException.AuthorizationException
            }
            .map { preferences ->
                // No type safety.
                kotlin.runCatching { UserDomainModel.create(preferences[userPref]!!) }.fold(
                    onSuccess = { it },
                    onFailure = { "" }
                )
            }.filterIsInstance()
    }

    suspend fun getUser(): UserDomainModel {
        val userString = context.dataStore.data.first()[userPref]
        return UserDomainModel.create(userString ?: "")
    }

    suspend fun setAuthToken(token: String) {
        authToken = token
      //  isUserLoggedIn = true
        context.dataStore.edit { settings ->
            settings[authTokenPref] = token
        }
    }

    private suspend fun getAuthToken(): String {
        return context.dataStore.data.first()[authTokenPref] ?: ""
    }

    suspend fun setIsLoggedIn(isLoggedIn: Boolean) {
        isUserLoggedIn = isLoggedIn
        context.dataStore.edit { settings ->
            settings[isLoggedInPref] = isLoggedIn
        }
    }

    suspend fun setIsOnBoardingViewed(isOnBoardingViewed: Boolean) {
        context.dataStore.edit { settings ->
            settings[isOnBoardingViewedPref] = isOnBoardingViewed
        }
    }

     suspend fun getIsLoggedIn(): Boolean {
        return context.dataStore.data.first()[isLoggedInPref] ?: false
//        return true
    }

     suspend fun getIsOnBoardingViewed(): Boolean {
        return context.dataStore.data.first()[isOnBoardingViewedPref] ?: false
    }

    suspend fun clearUser() {
        authToken = ""
        isUserLoggedIn = false
        context.dataStore.edit {settings ->
            settings[userPref] = ""
            settings[authTokenPref] = ""
            settings[isLoggedInPref] = false
        }
    }
}