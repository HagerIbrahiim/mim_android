package com.trianglz.mimar.modules.map.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
sealed class MapScreenMode : Parcelable {
    object EditAddress : MapScreenMode()
    object AddAddress : MapScreenMode()
}