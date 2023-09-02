package com.trianglz.mimar.modules.user.domain.model

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.trianglz.core.domain.exceptions.CustomException
import com.trianglz.core.domain.model.IdIdentifiedModel
import com.trianglz.mimar.modules.socket.domain.model.SocketModel
import com.trianglz.mimar.modules.addresses.domain.model.CustomerAddressDomainModel
import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.countries.domain.model.CountryDomainModel
import timber.log.Timber

data class UserDomainModel(
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
    val authToken: String? = "",
    val appointments: List<Any>? = listOf(),
    val country: CountryDomainModel? = null,
    val defaultAddress: CustomerAddressDomainModel? = null,
    val isSetCurrentLocation: Boolean = false,
    val cart: CartDomainModel?= null,
    val unseenNotificationsCount: Int?= null,
    ) : IdIdentifiedModel {

    override val uniqueId: Int
        get() = id ?: -1

    companion object {
        fun create(json: String): UserDomainModel {
            return try {
                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
                val adapter = moshi.adapter(UserDomainModel::class.java)
                adapter.fromJson(json)!!
            } catch (e: Exception) {
                Timber.tag("UserDomainModel").d(e)
                throw CustomException.AuthorizationException
            }
        }
    }


    override fun toString(): String {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(UserDomainModel::class.java)
        return adapter.toJson(this)
    }
}
