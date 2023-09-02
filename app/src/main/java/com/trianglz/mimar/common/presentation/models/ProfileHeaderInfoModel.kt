package com.trianglz.mimar.common.presentation.models

import com.trianglz.mimar.modules.account.presentation.model.BaseSettingModel

data class ProfileHeaderInfoModel(
    val name:  String,
    val image:  String?,
    val rating:  Int?= null,
    val showShimmer:  Boolean = false,
    ): BaseSettingModel