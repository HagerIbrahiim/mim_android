package com.trianglz.mimar.modules.socket.domain.model

interface SocketModel {
    fun create(json: String): SocketModel
}