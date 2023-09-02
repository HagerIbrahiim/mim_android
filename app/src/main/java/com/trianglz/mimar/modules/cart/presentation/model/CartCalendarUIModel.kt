package com.trianglz.mimar.modules.cart.presentation.model

import androidx.compose.runtime.MutableState
import com.trianglz.mimar.modules.calendar.presentation.model.CalendarUIModel

data class CartCalendarUIModel(
    val calendar: MutableState<CalendarUIModel?>
): BaseCartUIModel {
    override val uniqueId: Int
        get() = System.identityHashCode(this)
}
