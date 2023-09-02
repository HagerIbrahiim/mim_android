package com.trianglz.mimar.modules.filter.presenation.model

import androidx.compose.runtime.MutableState
import com.trianglz.core.domain.model.StringWrapper
import java.io.Serializable

interface BaseCheckboxItemUiModel : Serializable {
    val id: Int
    val title: StringWrapper
    val isChecked: MutableState<Boolean>
    val value: String
    val showShimmer: Boolean

}
