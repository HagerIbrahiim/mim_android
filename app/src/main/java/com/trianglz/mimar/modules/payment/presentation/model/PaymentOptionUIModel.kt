package com.trianglz.mimar.modules.payment.presentation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.modules.filter.presenation.model.BaseCheckboxItemUiModel

data class PaymentOptionUIModel(
    override val id: Int,
    val key: String?= null,
    override val title: StringWrapper,
    override var isChecked: MutableState<Boolean> = mutableStateOf(false),
    override val showShimmer: Boolean = false,
    override val value: String = ""
): BaseCheckboxItemUiModel