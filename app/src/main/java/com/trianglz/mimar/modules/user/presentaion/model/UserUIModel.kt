package com.trianglz.mimar.modules.user.presentaion.model

import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.cart.presentation.model.CartUIModel
import com.trianglz.mimar.modules.countries.presentation.model.CountryUIModel


data class UserUIModel(
    val id: Int? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val image: String? = null,
    val isNotifiable: Boolean? = null,
    val phoneNumber: String? = null,
    val isClassical: Boolean? = null,
    val gender: String? = null,
    val status: String? = null,
    val deletedAt: Any? = null,
    val phoneDialCode: String? = null,
    val isPhoneVerified: Boolean? = false,
    val isProfileCompleted: Boolean? = false,
    val mustChangePassword: Boolean? = false,
    val isEmailVerified: Boolean? = false,
    val dateOfBirth: String? = null,
    val appointments: List<Any>? = listOf(),
    val country: CountryUIModel? = null,
    val defaultAddress: CustomerAddressUIModel? = null,
    val cart: CartUIModel?= null,
    val unseenNotificationsCount: Int?= null,
    val showShimmer: Boolean = false,
    val isSetCurrentLocation: Boolean = false,
    val onClick: (id: Int) -> Unit = {}
) {
    val concatenatedName = "$firstName $lastName"
    companion object {

        fun getShimmerList(): List<UserUIModel> {
            val list: ArrayList<UserUIModel> = ArrayList()
            repeat(10) {
                list.add(
                    UserUIModel(
                        id = 0,
                        image = null,
                        email = null,
                        showShimmer = true,
                        onClick = {},
                    )
                )
            }
            return list
        }

    }
}