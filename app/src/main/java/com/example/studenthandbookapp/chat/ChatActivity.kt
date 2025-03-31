package com.example.studenthandbookapp.chat

import Message
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studenthandbookapp.R
import com.google.android.material.appbar.MaterialToolbar
import java.util.UUID

class ChatActivity : AppCompatActivity() {
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageEditText: AutoCompleteTextView
    private lateinit var sendButton: Button

    private val messages = mutableListOf<Message>()
    private lateinit var chatAdapter: ChatAdapter

    // FAQ data (question-answer pairs)
    private val faqList = listOf(
        "How do I reset my password?" to "You can reset your password by visiting the student portal and clicking 'Forgot Password'.",
        "Where can I find my course schedule?" to "Your course schedule is available in the student portal under 'My Courses' section.",
        "How do I contact my professor?" to "You can find professor contact information in the course syllabus or through the faculty directory.",
        "What are the library hours?" to "The library is open from 8 AM to 10 PM on weekdays and 10 AM to 6 PM on weekends.",
        "How do I apply for graduation?" to "Graduation applications can be submitted through the student portal under 'Academic Services'.",
        "Where can I find my grades?" to "Grades are posted in the student portal at the end of each semester."
    )

    private val faqQuestions = faqList.map { it.first }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        try {
            initializeViews()
            setupAutoComplete()
            setupTopAppBar()
            setupRecyclerView()
            setupClickListeners()
            addWelcomeMessage()
        } catch (e: Exception) {
            Toast.makeText(this, "Error initializing chat: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun setupTopAppBar() {
        topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initializeViews() {
        topAppBar = findViewById(R.id.topAppBar)
        recyclerView = findViewById(R.id.recycler_view_messages)
        messageEditText = findViewById(R.id.edit_text_message)
        sendButton = findViewById(R.id.button_send)
    }

    private fun setupAutoComplete() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, faqQuestions)
        messageEditText.setAdapter(adapter)
        messageEditText.threshold = 1
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(messages)  // Now correctly initialized without currentUserId
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity).apply {
                stackFromEnd = true  // This makes the chat start from bottom
            }
            adapter = chatAdapter
        }
    }

    private fun setupClickListeners() {
        sendButton.setOnClickListener {
            try {
                sendMessage()
            } catch (_: Exception) {
                Toast.makeText(this, "Error sending message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addWelcomeMessage() {
        // First message - Greeting
        addBotMessage("Hello! How can I help you today?")

        // Second message - Questions list (after 500ms delay)
        messageEditText.postDelayed({
            val questionsFormatted = faqQuestions.joinToString("\n") { "• $it" }
            addBotMessage("Common questions:\n$questionsFormatted")
        }, 500)
    }

    private fun sendMessage() {
        val messageText = messageEditText.text.toString().trim()
        if (messageText.isEmpty()) return

        addUserMessage(messageText)
        messageEditText.text.clear()

        // Simulate typing delay
        messageEditText.postDelayed({
            val answer = getFaqAnswer(messageText)
            addBotMessage(answer)
        }, 500)
    }

    private fun getFaqAnswer(question: String): String {
        return faqList.find { it.first.equals(question, ignoreCase = true) }?.second
            ?: "I'm sorry, I don't have an answer for that. Please contact the support desk for further assistance."
    }

    private fun addUserMessage(text: String) {
        val message = Message(
            id = UUID.randomUUID().toString(),
            text = text,
            senderId = "user",
            senderName = "You",
            timestamp = System.currentTimeMillis()
        )
        addMessageToChat(message)
    }

    private fun addBotMessage(text: String) {
        val message = Message(
            id = UUID.randomUUID().toString(),
            text = text,
            senderId = "support",
            senderName = "Support",
            timestamp = System.currentTimeMillis()
        )
        addMessageToChat(message)
    }

    private fun addMessageToChat(message: Message) {
        runOnUiThread {
            messages.add(message)
            chatAdapter.notifyItemInserted(messages.size - 1)
            recyclerView.scrollToPosition(messages.size - 1)
        }
    }
}