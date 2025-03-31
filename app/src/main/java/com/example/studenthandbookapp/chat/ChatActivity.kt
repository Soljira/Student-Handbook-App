package com.example.studenthandbookapp.chat

import Message
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studenthandbookapp.R
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var messageEditText: AutoCompleteTextView // Changed from EditText
    private lateinit var sendButton: Button

    private val messages = mutableListOf<Message>()
    private lateinit var chatAdapter: ChatAdapter // Renamed from adapter

    // FAQ data
    private val faqQuestions = listOf(
        "How do I reset my password?",
        "Where can I find my course schedule?",
        "How do I contact my professor?",
        "What are the library hours?",
        "How do I apply for graduation?",
        "Where can I find my grades?"
    )

    private val faqAnswers = mapOf(
        "How do I reset my password?" to "You can reset your password by visiting the student portal and clicking 'Forgot Password'.",
        "Where can I find my course schedule?" to "Your course schedule is available in the student portal under 'My Courses' section.",
        "How do I contact my professor?" to "You can find professor contact information in the course syllabus or through the faculty directory.",
        "What are the library hours?" to "The library is open from 8 AM to 10 PM on weekdays and 10 AM to 6 PM on weekends.",
        "How do I apply for graduation?" to "Graduation applications can be submitted through the student portal under 'Academic Services'.",
        "Where can I find my grades?" to "Grades are posted in the student portal at the end of each semester."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Initialize views
        recyclerView = findViewById(R.id.recycler_view_messages) ?: return
        messageEditText = findViewById(R.id.edit_text_message) ?: return // Make sure this is AutoCompleteTextView in XML
        sendButton = findViewById(R.id.button_send) ?: return

        // Setup autocomplete
        val autoCompleteAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            faqQuestions
        )
        messageEditText.setAdapter(autoCompleteAdapter)
        messageEditText.threshold = 1 // Start showing suggestions after 1 character

        // Setup RecyclerView
        chatAdapter = ChatAdapter(messages, "support") // Using "support" as senderId for bot messages
        recyclerView.adapter = chatAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add welcome message
        addBotMessage("Hello! How can I help you today? Here are some common questions:\n" +
                faqQuestions.joinToString("\n• ", "• "))

        // Scroll to bottom when new message arrives
        chatAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                recyclerView.smoothScrollToPosition(messages.size - 1)
            }
        })

        // Send message button click
        sendButton.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        val messageText = messageEditText.text.toString().trim()
        if (messageText.isEmpty()) return

        try {
            addUserMessage(messageText)

            // Clear input
            messageEditText.text.clear()

            // Get bot response safely
            val answer = faqAnswers[messageText] ?: "I'm sorry, I don't have an answer for that."

            // Simulate bot response
            messageEditText.postDelayed({
                addBotMessage(answer)
            }, 500)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun addUserMessage(text: String) {
        val message = Message(
            id = UUID.randomUUID().toString(),
            text = text,
            senderId = "user",
            senderName = "You",
            timestamp = System.currentTimeMillis()
        )
        messages.add(message)
        chatAdapter.notifyItemInserted(messages.size - 1)
    }

    private fun addBotMessage(text: String) {
        val message = Message(
            id = UUID.randomUUID().toString(),
            text = text,
            senderId = "support",
            senderName = "Support",
            timestamp = System.currentTimeMillis()
        )
        messages.add(message)
        chatAdapter.notifyItemInserted(messages.size - 1)
    }
}