package com.trianglz.mimar.modules.cart.presentation.model

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.mimar.common.presentation.tabs.models.TabItemUIModel

data class CartLocationSectionUIModel(
    val locations: SnapshotStateList<TabItemUIModel>,
): BaseCartUIModel {
    override val uniqueId: Int
        get() = System.identityHashCode(this)
}
