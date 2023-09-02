package com.trianglz.mimar.modules.user_home.presentation.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
@Immutable
data class GuestWelcomeSectionUIModel(
    val show: Boolean,
): BaseUserHomeUIModel {
    override val uniqueId: Int
        get() = System.identityHashCode(this)
}
