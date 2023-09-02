package com.trianglz.mimar.modules.user_home.presentation.model

import com.trianglz.core.domain.model.IdIdentifiedModel

interface BaseUserHomeUIModel : IdIdentifiedModel {
    override val uniqueId: Int
        get() = System.identityHashCode(this)
}
