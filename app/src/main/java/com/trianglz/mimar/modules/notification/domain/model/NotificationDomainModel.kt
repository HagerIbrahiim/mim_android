package com.trianglz.mimar.modules.notification.domain.model

import com.trianglz.core.domain.model.IdIdentifiedModel

data class NotificationDomainModel(
    val actionId: String?,
    val body: String?,
    val clickAction: String?,
    val createdAt: String?,
    val id: Int?,
    val seen: Boolean?,
    val title: String?,
    val registeredIds: List<String>? = null,
    ): IdIdentifiedModel {
    override val uniqueId: Int
        get() = id ?: -1
}