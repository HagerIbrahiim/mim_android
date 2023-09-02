package com.trianglz.mimar.modules.cart.presentation.model

import androidx.compose.runtime.MutableState

data class NoteUIModel(
    val note: MutableState<String>,
    val onClick: (String) -> Unit,
)
