package com.trianglz.mimar.modules.user.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.mimar.modules.addresses.data.model.CustomerAddressDataModel
import com.trianglz.mimar.modules.cart.data.remote.model.CartDataModel
import com.trianglz.mimar.modules.countries.data.model.CountryDataModel

@Keep
data class UserDataModel(
    @Json(name = "id")
    val id: Int?= null,
    @Json(name = "email")
    val email: String?= null,
    @Json(name = "first_name")
    val firstName: String?= null,
    @Json(name = "last_name")
    val lastName: String?= null,
    @Json(name = "image")
    val image: String?= null,
    @Json(name = "is_notifiable")
    val isNotifiable: Boolean?= null,
    @Json(name = "phone_number")
    val phoneNumber: String?= null,
    @Json(name = "is_classical")
    val isClassical: Boolean?= null,
    @Json(name = "gender")
    val gender: String?= null,
    @Json(name = "status")
    val status: String?= null,
    @Json(name = "deleted_at")
    val deletedAt: Any?= null,
    @Json(name = "phone_dial_code")
    val phoneDialCode: String?= null,
    @Json(name = "is_phone_verified")
    val isPhoneVerified: Boolean?= false,
    @Json(name = "is_profile_completed")
    val isProfileCompleted: Boolean?= false,
    @Json(name = "must_change_password")
    val mustChangePassword: Boolean?= false,
    @Json(name = "is_email_verified")
    val isEmailVerified: Boolean?=false,
    @Json(name = "date_of_birth")
    val dateOfBirth: String?= null,
    @Json(name = "auth_token")
    val authToken: String = "",
    @Json(name = "appointments")
    val appointments: List<Any>?= listOf(),
    @Json(name = "country")
    val country: CountryDataModel? = null,
    @Json(name = "default_address")
    val defaultAddress: CustomerAddressDataModel?= null,
    @Json(name = "cart")
    val cart: CartDataModel?= null,
    @Json(name = "customer_app_unseen_notifications_count")
    val unseenNotificationsCount: Int?= null,
    )
