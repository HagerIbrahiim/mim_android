/*
 * *
 *  * Created by Ahmed Awad on 1/3/23, 11:56 AM
 *
 */

package com.trianglz.mimar.modules.sign_up.domain.repository

import com.trianglz.mimar.modules.sign_up.data.retrofit.request.SignUpRequestModel
import com.trianglz.mimar.modules.sign_up.domain.model.SignUpDomainModel
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel

interface SignUpRepo {
    suspend fun signUp(signUpDomainModel: SignUpDomainModel): UserDomainModel
}