package com.trianglz.mimar.modules.addresses.ui.model

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.trianglz.core.domain.model.IdIdentifiedModel
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.enums.Locales
import com.trianglz.core.presentation.utils.getAppLocale
import com.trianglz.core_compose.presentation.pagination.model.ShimmerModel
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.addresses_list.presentation.model.BaseAddressModel
import kotlinx.parcelize.Parcelize


@Parcelize
data class CustomerAddressUIModel(
    val id: Int,
    val title: StringWrapper? = StringWrapper(""),
    val buildingNumber: String? = null,
    val city: String? = null,
    val isDefault: Boolean? = false,
    val landmark: String? = null,
    val lat: Double? = null,
    val lng: Double? = null,
    val district: String? = null,
    val streetName: String? = null,
    val secondaryNum: String?= null,
    var country: String? = null,
    val isSupported: Boolean = true,
    override val showShimmer: Boolean = false,
    val isChecked: Boolean = false,
    ) : Parcelable, IdIdentifiedModel, ShimmerModel,
    BaseAddressModel {
    override val uniqueId: Int
        get() = id

    val displayedCustomerAddress =
        "${buildingNumber ?: ""} ${streetName ?: ""} ${district ?: ""} ${city ?: ""}, $country"


    companion object {
        fun getShimmerList(): List<CustomerAddressUIModel> {
            val list: ArrayList<CustomerAddressUIModel> = ArrayList()
            repeat(10) {
                list.add(
                    CustomerAddressUIModel(id = it,
                        showShimmer = true
                    )
                )
            }
            return list
        }

        fun getCurrentLocationItem(
            isDefault: Boolean? = false,
            lat: Double? = null,
            lng: Double? = null,
            isChecked: Boolean = false,

        ) =
        CustomerAddressUIModel(
            id = -1,
            title = StringWrapper(R.string.current_location),
            lat = lat,
            lng = lng,
            isChecked = isChecked,
            isDefault = isDefault,
            isSupported = true,
            )

    }
}


