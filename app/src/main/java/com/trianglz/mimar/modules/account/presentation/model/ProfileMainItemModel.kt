package com.trianglz.mimar.modules.account.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.Modifier

data class ProfileMainItemModel(
    val modifier: Modifier = Modifier,
    @StringRes val title: Int,
    @DrawableRes val icon: Int? = null,
    @StringRes val subTitle: Int?=null,
    val itemPosition: ProfileItemPosition ,
    val hasSwitch: Boolean = false,
    val isSwitchChecked: Boolean? = null,
    val isSubSetting: Boolean = false,
    val onClick: (Boolean?) -> Unit
):BaseSettingModel
