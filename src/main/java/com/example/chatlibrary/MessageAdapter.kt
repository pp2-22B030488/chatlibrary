package com.example.chatlibrary

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.chatlibrary.databinding.ItemMessageBinding

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val messages = mutableListOf<Message>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun addMessage(message: Message) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    class MessageViewHolder(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.messageText.text = message.content
            binding.messageTime.text = message.timestamp


            val layoutParams = binding.messageContainer.layoutParams as FrameLayout.LayoutParams

            if (message.sender == "user") {
                layoutParams.gravity = Gravity.END
                binding.messageContainer.setBackgroundResource(R.drawable.rounded_message_user)
            } else {
                layoutParams.gravity = Gravity.START
                binding.messageContainer.setBackgroundResource(R.drawable.rounded_message_server)
            }

            binding.messageContainer.layoutParams = layoutParams
        }


    }
}
