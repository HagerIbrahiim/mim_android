package com.trianglz.mimar.modules.user_home.presentation.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.trianglz.mimar.modules.categories.presentation.model.CategoryUIModel

@Stable
@Immutable
data class CategoriesSectionUIModel(
    val list: List<CategoryUIModel>,
    val showShimmer: Boolean = false,
    val onSeeMoreClicked: () -> Unit = {}
): BaseUserHomeUIModel {
    override val uniqueId: Int
        get() = System.identityHashCode(this)

}
