package com.trianglz.mimar.modules.time.presentation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core_compose.presentation.pagination.model.ShimmerModel
import com.trianglz.mimar.modules.cart.presentation.model.BaseCartUIModel

data class TimeUIModel(
    val id: Int,
    val title: String = "",
    val isSelected: MutableState<Boolean> = mutableStateOf(false),
    override val showShimmer: Boolean = false,
    val onClick: (id: Int) -> Unit = {}
): ShimmerModel {
    override val uniqueId: Int
        get() = id
    companion object {
        fun getShimmerList(count: Int = 10): List<TimeUIModel> {
            val list: ArrayList<TimeUIModel> = ArrayList()
            repeat(count) {
                list.add(
                    TimeUIModel(
                        id = it,
                        showShimmer = true,
                    )
                )
            }
            return list
        }
    }
}
