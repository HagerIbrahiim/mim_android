package com.trianglz.mimar.modules.cart.data.remote.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.socket.domain.model.SocketModel
import com.trianglz.mimar.modules.cart.data.remote.model.CartDataModel
import timber.log.Timber

@Keep
class CartResponseModel(
    @Json(name = "cart")
    val cart: CartDataModel?
) : SuccessMessageResponse(), SocketModel {
    companion object {
        fun create(json: String): CartResponseModel {
            return try {
                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
                val adapter = moshi.adapter(CartResponseModel::class.java)
                adapter.fromJson(json)!!
            } catch (e: Exception) {
                Timber.tag("CartResponseModel").d(e)
                throw Exception()
            }
        }
    }

    override fun create(json: String): SocketModel {
        return CartResponseModel.create(json)
    }

    override fun toString(): String {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(CartResponseModel::class.java)
        return adapter.toJson(this)
    }
}