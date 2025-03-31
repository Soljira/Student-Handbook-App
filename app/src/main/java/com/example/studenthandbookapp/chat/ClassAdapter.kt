package com.example.studenthandbookapp.chat

import Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studenthandbookapp.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatAdapter(
    private val messages: MutableList<Message>,
    private val currentUserId: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_RECEIVED = 1
        private const val VIEW_TYPE_SENT = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_RECEIVED -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_sent, parent, false)
                SentMessageHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_received, parent, false)
                ReceivedMessageHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position !in 0 until messages.size) return

        val message = messages[position]

        when (holder) {
            is SentMessageHolder -> holder.bind(message)
            is ReceivedMessageHolder -> holder.bind(message)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int): Int {
        return if (position in 0 until messages.size && messages[position].senderId == currentUserId) {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVED
        }
    }

    inner class SentMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.text_message_body)
        private val timeText: TextView = itemView.findViewById(R.id.text_message_time)
        private val messageImage: ImageView = itemView.findViewById(R.id.image_message)

        fun bind(message: Message) {
            messageText.text = message.text
            timeText.text = SimpleDateFormat("hh:mm a", Locale.getDefault())
                .format(Date(message.timestamp))
            messageImage.visibility = View.GONE
        }
    }

    inner class ReceivedMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.text_message_body)
        private val timeText: TextView = itemView.findViewById(R.id.text_message_time)
        private val userName: TextView = itemView.findViewById(R.id.text_message_name)
        private val messageImage: ImageView = itemView.findViewById(R.id.image_message)
        private val profileImage: ImageView = itemView.findViewById(R.id.image_message_profile)

        fun bind(message: Message) {
            messageText.text = message.text
            timeText.text = SimpleDateFormat("hh:mm a", Locale.getDefault())
                .format(Date(message.timestamp))
            userName.text = message.senderName
            messageImage.visibility = View.GONE

            // Set different icons based on sender
            profileImage.setImageResource(
                if (message.senderId == "support") R.drawable.ic_email
                else R.drawable.ic_profile_placeholder
            )
        }
    }
}