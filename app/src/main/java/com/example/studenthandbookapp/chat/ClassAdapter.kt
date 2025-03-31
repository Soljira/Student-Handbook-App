package com.example.studenthandbookapp.chat

import Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.studenthandbookapp.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatAdapter(
    private val messages: MutableList<Message>,
    private val currentUserId: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_SENT = 1
        private const val VIEW_TYPE_RECEIVED = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_received, parent, false) // Fix this
            SentMessageHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_sent, parent, false) // Fix this
            ReceivedMessageHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        if (holder.itemViewType == VIEW_TYPE_SENT) {
            (holder as SentMessageHolder).bind(message)
        } else {
            (holder as ReceivedMessageHolder).bind(message)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].senderId == currentUserId) {
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
            messageImage.visibility = View.GONE // Hide image view in FAQ version
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
            messageImage.visibility = View.GONE // Hide image view in FAQ version

            // Set support icon for bot messages
            if (message.senderId == "support") {
                profileImage.setImageResource(R.drawable.ic_profile_placeholder)
            } else {
                profileImage.setImageResource(R.drawable.ic_profile_placeholder)
            }
        }
    }
}