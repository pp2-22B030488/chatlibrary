package com.example.chatlibrary

import android.content.Context
import android.content.Intent
import com.example.chatlibrary.ChatActivity

class ChatLibrary {

    fun start(context: Context) {
        val intent = Intent(context, ChatActivity::class.java)
        context.startActivity(intent)
    }
}
