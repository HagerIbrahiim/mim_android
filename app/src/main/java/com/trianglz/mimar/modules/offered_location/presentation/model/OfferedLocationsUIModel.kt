package com.trianglz.mimar.modules.offered_location.presentation.model

import android.os.Parcelable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.modules.filter.presenation.model.BaseCheckboxItemUiModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.io.Serializable

@Parcelize
data class OfferedLocationsUIModel(
    override val id: Int,
    override val title: StringWrapper,
    override val isChecked: @RawValue MutableState<Boolean> = mutableStateOf(false),
    override val value: String,
    override val showShimmer: Boolean = false,
): BaseCheckboxItemUiModel, Parcelable {

    companion object {
        fun getShimmerList(): List<OfferedLocationsUIModel> {
            val list: ArrayList<OfferedLocationsUIModel> = ArrayList()
            repeat(2) {
                list.add(
                    OfferedLocationsUIModel(
                        id = it,
                        value = "",
                        title = StringWrapper(""),
                        showShimmer = true
                    )
                )
            }
            return list
        }
    }
}