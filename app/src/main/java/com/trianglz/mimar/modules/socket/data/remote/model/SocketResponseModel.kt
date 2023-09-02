package com.trianglz.mimar.modules.socket.data.remote.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.trianglz.mimar.modules.socket.domain.model.SocketModel
import timber.log.Timber

@Keep
data class SocketResponseModel(
    @Json(name = "type")
    val type: String?,
    @Json(name = "reason")
    val reason: String?,
    @Json(name = "reconnect")
    val reconnect: Boolean?,
    @Json(name = "identifier")
    val identifier: String?,
    @Json(name = "message")
    val message: Any?,
): SocketModel {
    companion object {

        fun create(json: String): SocketResponseModel {
            return try {
                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
                val adapter = moshi.adapter(SocketResponseModel::class.java)
                adapter.fromJson(json)!!
            } catch (e: Exception) {
                Timber.tag("SocketResponseModel").d(e)
                throw Exception()
            }
        }
    }

    override fun create(json: String): SocketModel {
        return SocketResponseModel.create(json)
    }

    override fun toString(): String {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(SocketResponseModel::class.java)
        return adapter.toJson(this)
    }
}