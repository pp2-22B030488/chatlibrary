package com.example.chatlibrary

import okhttp3.*
import java.io.IOException

class WebSocketManager {
    private var webSocket: WebSocket? = null
    private val client = OkHttpClient()

    fun connectToWebSocket(url: String, listener: WebSocketListener) {
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, listener)
    }

    fun sendMessage(message: String) {
        webSocket?.send(message)
    }

    fun closeConnection() {
        webSocket?.close(1000, "Closing connection")
    }
}