package com.trianglz.mimar.modules.cart.presentation.model

import androidx.compose.runtime.State

data class CartNoteSectionUIModel(
    val noteUIModel: State<NoteUIModel?>
): BaseCartUIModel {
    override val uniqueId: Int
        get() = System.identityHashCode(this)
}
