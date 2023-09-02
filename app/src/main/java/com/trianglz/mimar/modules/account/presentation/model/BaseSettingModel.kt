package com.trianglz.mimar.modules.account.presentation.model

import com.trianglz.core.domain.model.IdIdentifiedModel

interface BaseSettingModel : IdIdentifiedModel {
    override val uniqueId: Int
        get() = System.identityHashCode(this)
}
