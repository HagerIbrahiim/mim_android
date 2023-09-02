package com.trianglz.mimar.modules.services.domain.model

import android.os.Parcelable
import com.trianglz.mimar.R
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class ServiceStatus(val key: String?, val name: Int?=null, val icon: Int?=null): Parcelable {
    object Scheduled: ServiceStatus("scheduled")
    object Unscheduled: ServiceStatus("unscheduled")
    object Upcoming : ServiceStatus("upcoming", R.string.upcoming,)
    object Ongoing : ServiceStatus("ongoing", R.string.ongoing,)
    object Done : ServiceStatus("completed", R.string.done, R.drawable.ic_check_icon)
    object Cancelled : ServiceStatus("canceled", R.string.cancelled, R.drawable.cancelled_icon)
    object Reported : ServiceStatus("reported", R.string.reported, R.drawable.flag_icon)
}


fun String?.toServiceStatus(): ServiceStatus {
    return when (this) {
        ServiceStatus.Scheduled.key -> ServiceStatus.Scheduled
        ServiceStatus.Upcoming.key -> ServiceStatus.Upcoming
        ServiceStatus.Ongoing.key -> ServiceStatus.Ongoing
        ServiceStatus.Reported.key -> ServiceStatus.Reported
        ServiceStatus.Done.key -> ServiceStatus.Done
        ServiceStatus.Cancelled.key -> ServiceStatus.Cancelled
        else -> ServiceStatus.Unscheduled
    }
}