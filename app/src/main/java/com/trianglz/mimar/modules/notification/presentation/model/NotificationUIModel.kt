package com.trianglz.mimar.modules.notification.presentation.model

import com.trianglz.core.domain.enum.SimpleDateFormatEnum
import com.trianglz.core.domain.extensions.formatDate
import com.trianglz.core.domain.model.IdIdentifiedModel
import com.trianglz.core_compose.presentation.pagination.model.PaginatedModel
import com.trianglz.core_compose.presentation.pagination.model.ShimmerModel
import com.trianglz.mimar.modules.notification.domain.model.NotificationAction
import com.trianglz.mimar.common.domain.enum.SimpleDateFormatData.DayMonthYear2
data class NotificationUIModel(
    val actionId: String? = null,
    val body: String? = null,
    val clickAction: NotificationAction? = null,
    val createdAt: String? = null,
    val id: Int? = -1,
    val seen: Boolean? = null,
    val title: String? = null,
    override val showShimmer: Boolean = false,
    val onNotificationClicked: (NotificationUIModel) -> Unit = {}

    ) : IdIdentifiedModel, ShimmerModel, PaginatedModel {
    override val uniqueId: Int
        get() = id ?: -1


    val formattedDate = createdAt.formatDate(
        inputFormat = SimpleDateFormatEnum.DateInput2,
        returnFormat = DayMonthYear2
    )

    companion object {
        fun getShimmerList(): List<NotificationUIModel> {
            val list: ArrayList<NotificationUIModel> = ArrayList()
            repeat(10) {
                list.add(
                    NotificationUIModel(id = it, showShimmer = true, body = "")
                )
            }
            return list
        }
    }
}



