package com.trianglz.mimar.modules.specilaities.presenation.model

import android.os.Parcelable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.common.presentation.selectables.model.SelectableUIModel
import com.trianglz.mimar.modules.filter.presenation.model.BaseCheckboxItemUiModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class SpecialtiesUIModel(
    override val id: Int,
    override val title: StringWrapper,
    override val isChecked: @RawValue MutableState<Boolean> = mutableStateOf(false),
    override val showShimmer: Boolean = false,
    override val value: String = ""
): BaseCheckboxItemUiModel, SelectableUIModel, Parcelable {
    override val uniqueId: Int
        get() = id

    companion object{
        fun getShimmerList(count: Int = 10): List<SpecialtiesUIModel> {
            val list: ArrayList<SpecialtiesUIModel> = ArrayList()
            repeat(count) {
                list.add(
                    SpecialtiesUIModel(
                        id = it,
                        title = StringWrapper("Speciality"),
                        showShimmer = true,
                    )
                )
            }
            return list
        }
    }
}



