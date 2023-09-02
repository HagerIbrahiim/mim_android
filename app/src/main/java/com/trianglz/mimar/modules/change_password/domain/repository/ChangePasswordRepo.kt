package com.trianglz.mimar.modules.change_password.domain.repository

import com.trianglz.mimar.modules.change_password.domain.model.ChangePasswordDomainModel
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel

interface ChangePasswordRepo {
    suspend fun changePassword(changePasswordDomainModel: ChangePasswordDomainModel): UserDomainModel
}