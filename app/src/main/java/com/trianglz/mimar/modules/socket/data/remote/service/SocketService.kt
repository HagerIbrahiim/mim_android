package com.trianglz.mimar.modules.socket.data.remote.service

import com.trianglz.mimar.modules.socket.domain.model.SocketModel
import kotlinx.coroutines.flow.Flow

interface SocketService<T: SocketModel> {

    suspend fun connect(params: Map<String,String>, onConnectSuccessfully: () -> Unit, jsonToModel: (String) -> T?): Flow<T>
    fun sendMessageToServer(msg: String)
    fun reconnect()
    fun disconnect()
}