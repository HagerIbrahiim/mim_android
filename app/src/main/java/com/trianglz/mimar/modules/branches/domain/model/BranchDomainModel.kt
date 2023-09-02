package com.trianglz.mimar.modules.branches.domain.model

import android.os.Parcelable
import com.trianglz.core.domain.model.BaseUpdatableItem
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek
import kotlinx.parcelize.RawValue

@Parcelize
open class BranchDomainModel(
    open val  id: Int = -1,
    open val  name: String = "",
    open val  image: String = "",
    open val  location: String = "",
    open val  reviewsCount: Int? = null,
    open val  rating: Float? = null,
    open val  isFavorite: Boolean = false,
    @Transient
    open val  offeredLocation: OfferedLocationType = OfferedLocationType.Home,
    open val  serviceProviderLogo: String? = null,
    open val  offDays: List<String> = emptyList(),
    open val  paymentMethods: String? = null,
    @Transient
    open val  statusType: @RawValue BranchStatusType?= null,
    ): BaseUpdatableItem, Parcelable {
    override  val  uniqueId: Int
        get() = id
}

