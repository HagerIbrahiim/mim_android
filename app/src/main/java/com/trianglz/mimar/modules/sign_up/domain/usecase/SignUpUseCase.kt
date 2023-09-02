/*
 * *
 *  * Created by Ahmed Awad on 1/3/23, 12:05 PM
 *
 */

package com.trianglz.mimar.modules.sign_up.domain.usecase

import com.trianglz.mimar.modules.sign_up.domain.model.SignUpDomainModel
import com.trianglz.mimar.modules.sign_up.domain.repository.SignUpRepo
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repo: SignUpRepo) {

    suspend fun execute(signUpDomainModel: SignUpDomainModel) : UserDomainModel{
        return repo.signUp(signUpDomainModel)
    }

}