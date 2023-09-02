package com.trianglz.mimar.common.presentation.selectables.model

import androidx.annotation.DrawableRes


interface SelectableWithIconUIModel: SelectableUIModel {
    @get:DrawableRes
    val icon: Int
}