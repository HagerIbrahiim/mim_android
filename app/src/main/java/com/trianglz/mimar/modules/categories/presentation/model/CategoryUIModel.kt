package com.trianglz.mimar.modules.categories.presentation.model

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.pagination.model.PaginatedModel
import com.trianglz.core_compose.presentation.pagination.model.ShimmerModel
import com.trianglz.mimar.common.presentation.selectables.model.SelectableUIModel
import com.trianglz.mimar.common.presentation.ui.theme.EtonBlue
import com.trianglz.mimar.common.presentation.ui.theme.LightSteelBlue
import com.trianglz.mimar.common.presentation.ui.theme.TropicalViolet

data class CategoryUIModel(
    val id: Int,
    val image: String = "",
    val name: String = "",
    override var isChecked: MutableState<Boolean> = mutableStateOf(false),
    val color: @Composable () -> Color = { MaterialTheme.colors.secondary.copy(alpha = 0.2f) },
    override val showShimmer: Boolean = false,
): PaginatedModel, SelectableUIModel {
    override val uniqueId: Int
        get() = id
    override val title: StringWrapper
        get() = StringWrapper(name)

    companion object {

        fun getShimmerList(count: Int = 10): List<CategoryUIModel> {
            val list: ArrayList<CategoryUIModel> = ArrayList()
            repeat(count) {
                list.add(
                    CategoryUIModel(
                        id = it,
                        name = "Category",
                        showShimmer = true,
                    )
                )
            }
            return list
        }

        @Composable
        fun Int.getCategoryColor(): Color{
            return when {
                (this + 1) % 4 == 0 -> {
                    EtonBlue.copy(alpha = 0.2f)
                }
                (this + 1) % 3 == 0 -> {
                    TropicalViolet.copy(alpha = 0.2f)
                }
                (this + 1) % 2 == 0 -> {
                    LightSteelBlue.copy(alpha = 0.2f)
                }

                else -> {
                    MaterialTheme.colors.secondary.copy(alpha = 0.2f)
                }
            }
        }

    }
}
