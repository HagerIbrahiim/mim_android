package com.trianglz.mimar.modules.account.presentation.model

sealed class ProfileItemPosition {
    object Top: ProfileItemPosition()
    object Bottom: ProfileItemPosition()
    object Middle: ProfileItemPosition()

}