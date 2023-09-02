package com.trianglz.mimar.modules.change_password.domain.usecase

import com.trianglz.mimar.modules.change_password.domain.model.ChangePasswordDomainModel
import com.trianglz.mimar.modules.change_password.domain.repository.ChangePasswordRepo
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import javax.inject.Inject


class ChangePasswordUseCase @Inject constructor(private val repo: ChangePasswordRepo) {
    suspend fun execute(changePasswordDomainModel: ChangePasswordDomainModel): UserDomainModel {
        return repo.changePassword(changePasswordDomainModel)
    }
}