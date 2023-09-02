package com.trianglz.mimar.common.data.interceptor

import com.trianglz.core.data.retrofit.interceptor.AuthenticationInterceptor
import com.trianglz.mimar.modules.user.data.local.UserLocalDataSource
import javax.inject.Inject

class AuthenticationInterceptorImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource

): AuthenticationInterceptor() {
    override fun getToken(): String {
        return try {
            userLocalDataSource.getAuthToken()
        } catch (e: Exception) {
//            throw AuthorizationException
            ""
        }
    }

}