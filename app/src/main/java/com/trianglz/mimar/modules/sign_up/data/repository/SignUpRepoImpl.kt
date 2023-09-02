/*
 * *
 *  * Created by Ahmed Awad on 1/3/23, 12:00 PM
 *
 */

package com.trianglz.mimar.modules.sign_up.data.repository

import android.util.Log
import com.trianglz.mimar.modules.sign_up.data.mapper.toRequest
import com.trianglz.mimar.modules.sign_up.data.remote.SignUpRemoteDataSource
import com.trianglz.mimar.modules.sign_up.domain.model.SignUpDomainModel
import com.trianglz.mimar.modules.sign_up.domain.repository.SignUpRepo
import com.trianglz.mimar.modules.user.data.mapper.toDomainModel
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import javax.inject.Inject

class SignUpRepoImpl @Inject constructor(private val dataSource: SignUpRemoteDataSource) :
    SignUpRepo {
    override suspend fun signUp(signUpDomainModel: SignUpDomainModel) : UserDomainModel {
        return dataSource.signUp(signUpDomainModel.toRequest()).toDomainModel()
    }
}