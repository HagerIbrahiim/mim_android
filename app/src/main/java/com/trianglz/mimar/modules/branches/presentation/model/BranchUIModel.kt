package com.trianglz.mimar.modules.branches.presentation.model

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.core_compose.presentation.pagination.model.PaginatedModel
import com.trianglz.mimar.modules.branches.domain.model.BranchStatusType
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType
import com.trianglz.mimar.modules.payment.domain.model.PaymentMethodType
import com.trianglz.mimar.modules.payment.presentation.model.PaymentMethodUIModel
import com.trianglz.mimar.modules.search.presentation.model.BaseSearchModel
import java.time.DayOfWeek
import kotlinx.parcelize.RawValue

open class BranchUIModel(
    open val  id: Int = 0,
    open val  name: String = "",
    open val  image: String = "",
    open val  location: String = "",
    open val  reviewsCount: Int = 0,
    open val  rating: Float? = null,
    open val  offeredLocation: OfferedLocationType = OfferedLocationType.Home,
    open val  isFavorite: Boolean = false,
    open val  serviceProviderLogo: String = "",
    open val  offDays: List<String> = listOf(),
    open val  paymentMethods: String? = null,
    open  val statusType: @RawValue BranchStatusType?= null,
    open val  onFavoriteClick: (item: BranchUIModel) -> Unit={},
    open val  onClick: (id: Int) -> Unit = {},
    override val showShimmer: Boolean = false,
    ): PaginatedModel, BaseSearchModel {


    override val uniqueId: Int
        get() = id

    companion object {

        fun getShimmerList(count: Int = 10): SnapshotStateList<BranchUIModel> {
            val list: SnapshotStateList<BranchUIModel> = SnapshotStateList()
            repeat(count) {
                list.add(
                    BranchUIModel(
                        id = it,
                        showShimmer = true,
                        onClick = {},
                        onFavoriteClick = {}
                    )
                )
            }
            return list
        }
    }
}

