package com.example.chatlibrary

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var adapter: MessageAdapter
    private lateinit var webSocketManager: WebSocketManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chatlibrary_activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        messageInput = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendButton)

        adapter = MessageAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        webSocketManager = WebSocketManager()

        webSocketManager.connectToWebSocket("wss://echo.websocket.org/", object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                runOnUiThread {
                    adapter.addMessage(Message(text, "server", getCurrentTime()))
                }
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                runOnUiThread {
                    adapter.addMessage(Message("Connected to server", "server", getCurrentTime()))
                }
            }
        })

        sendButton.setOnClickListener {
            val message = messageInput.text.toString()
            if (message.isNotEmpty()) {
                webSocketManager.sendMessage(message)
                adapter.addMessage(Message(message, "user", getCurrentTime()))
                messageInput.text.clear()
            }
        }
    }
    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }


    override fun onDestroy() {
        super.onDestroy()
        webSocketManager.closeConnection()
    }
}
