package com.trianglz.mimar.modules.change_password.data.mapper

import com.trianglz.mimar.modules.change_password.data.retrofit.request.ChangePasswordRequestModel
import com.trianglz.mimar.modules.change_password.domain.model.ChangePasswordDomainModel


fun ChangePasswordDomainModel.toRequest(): ChangePasswordRequestModel {
    return ChangePasswordRequestModel(newPassword, oldPassword,)
}