package com.trianglz.mimar.modules.change_password.data.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ChangePasswordRequestModel(
    @Json(name = "new_password")
    val newPassword: String,
    @Json(name = "old_password")
    val oldPassword: String,
)
