package com.trianglz.mimar.modules.user_home.presentation.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.trianglz.mimar.modules.appointments.presentation.model.AppointmentUIModel

@Stable
@Immutable
data class LastAppointmentSectionUIModel(
    val appointment: AppointmentUIModel? = null,
    val showShimmer: Boolean = false,
): BaseUserHomeUIModel {
    override val uniqueId: Int
        get() = System.identityHashCode(this)

}
