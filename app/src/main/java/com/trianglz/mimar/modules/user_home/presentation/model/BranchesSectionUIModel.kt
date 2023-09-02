package com.trianglz.mimar.modules.user_home.presentation.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel

@Stable
@Immutable
data class BranchesSectionUIModel(
    val list: SnapshotStateList<BranchUIModel>,
    val branchSectionType: BranchSectionType,
    val showShimmer: Boolean = false,
    val onSeeMoreClicked: () -> Unit = {}
    ): BaseUserHomeUIModel {
    override val uniqueId: Int
        get() = System.identityHashCode(this)

}
