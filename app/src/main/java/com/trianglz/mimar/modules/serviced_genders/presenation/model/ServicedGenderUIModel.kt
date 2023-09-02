package com.trianglz.mimar.modules.serviced_genders.presenation.model

import android.os.Parcelable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.modules.filter.presenation.model.BaseCheckboxItemUiModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
@Parcelize
data class ServicedGenderUIModel(
    override val id: Int,
    override val title: StringWrapper,
    override val isChecked: @RawValue MutableState<Boolean> = mutableStateOf(false),
    override val value: String,
    override val showShimmer: Boolean= false,
): BaseCheckboxItemUiModel, Parcelable

