package com.trianglz.mimar.modules.cart.presentation.model

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.mimar.modules.time.presentation.model.TimeUIModel

data class CartAvailableTimesUIModel(
    val availableTimeSlots: SnapshotStateList<TimeUIModel>
): BaseCartUIModel {
    override val uniqueId: Int
        get() = System.identityHashCode(this)
}
