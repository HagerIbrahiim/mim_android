package com.trianglz.mimar.modules.socket.data.remote.service

import android.annotation.SuppressLint
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.trianglz.mimar.modules.socket.data.remote.model.SocketResponseModel
import com.trianglz.mimar.modules.socket.di.qualifiers.SocketUrl
import com.trianglz.mimar.modules.socket.domain.model.SocketModel
import com.trianglz.mimar.modules.user.data.local.UserLocalDataSource
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import okhttp3.*
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.inject.Inject

class SocketServiceImpl<T : SocketModel> @Inject constructor(
    @SocketUrl val url: String,
    userLocalDataSource: UserLocalDataSource
) : SocketService<T> {
    val uri = URI(url)
    val headers = mapOf(
        "cookie" to userLocalDataSource.getAuthToken(),
    )
    private var webSocketClient: WebSocketClient? = null

    private val flow = MutableSharedFlow<T>(0, Int.MAX_VALUE, BufferOverflow.SUSPEND)

    override suspend fun connect(
        params: Map<String, String>,
        onConnectSuccessfully: () -> Unit,
        jsonToModel: (String) -> T?
    ): Flow<T> {
        if (webSocketClient == null || webSocketClient?.isOpen == false) {
            webSocketClient = object : WebSocketClient(uri, headers) {
                @SuppressLint("SuspiciousIndentation")
                override fun onMessage(message: String?) {
                    Log.d("test_web_socket_main", "$message")
                    val response = SocketResponseModel.create(message ?: "")
                    if (response.reason.isNullOrEmpty()) {

                        if (response.message == null || response.type == "ping") return

                        kotlin.runCatching {
                            if (response.message is Map<*, *>) {

                                val moshi = Moshi.Builder().build()
                                val type = Types.newParameterizedType(
                                    Map::class.java,
                                    String::class.java,
                                    Any::class.java
                                )
                                val adapter: JsonAdapter<Map<String, Any>> = moshi.adapter(type)

                                val jsonString =
                                    adapter.toJson(response.message as Map<String, Any>)
                                val model = jsonToModel.invoke(jsonString)
                                if (model != null) {
                                    flow.tryEmit(model)
                                }
                            }
                        }

                    } else {
                        Log.e("test_web_socket_main", "${response.reason}")

//                        throw Exception(response.reason)
                    }

                }

                override fun onError(ex: Exception?) {
                    Log.e("test_web_socket_main", "$ex")
                    disconnect()
                }
                override fun onOpen(handshakedata: ServerHandshake?) {
                    Log.d("test_web_socket_main", "onOpen ref of webSocketClient = ${System.identityHashCode(webSocketClient)}")
                    onConnectSuccessfully.invoke()
                }
                override fun onClose(code: Int, reason: String?, remote: Boolean) {
                    Log.d("test_web_socket_main", "on close")
                }
            }
            webSocketClient?.connect()
        }
        return flow
    }

    override fun reconnect() {
        if (webSocketClient?.connection?.isClosed == true) {
            Log.d("test_web_socket_main", "on reconnect")
            webSocketClient?.reconnect()
        }
    }

    override fun sendMessageToServer(msg: String) {
        Log.d("test_web_socket_main", "msg = $msg")
        webSocketClient?.send(msg)
    }

    override fun disconnect() {
        webSocketClient?.close()
    }
}