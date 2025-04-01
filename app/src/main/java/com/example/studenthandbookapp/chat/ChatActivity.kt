package com.example.studenthandbookapp.chat

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studenthandbookapp.R
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch
import java.util.UUID

class ChatActivity : AppCompatActivity() {
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageEditText: AutoCompleteTextView
    private lateinit var sendButton: Button

    private val messages = mutableListOf<Message>()
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var database: ChatDatabase
    private lateinit var messageDao: MessageDao

    private val faqList = listOf(
        // PART 1: WELCOME AND OVERVIEW
        "What is the PHINMA Education Network?" to "Welcome to the PHINMA Education Network! Your entry to this PHINMA Education Network (PEN) school ushers you likewise into a group with over 40 years of service in professional management. As a student of the School you are expected to meet certain standards of academic performance. You are likewise expected to adhere to prescribed norms and behavioral standards and values as described in the rules and regulations and policies of the School. You are now a member of the PHINMA Education Network.",
        "What is the Mission of the PHINMA Education Network?" to "Through the application of effective management in institutions of higher and basic learning, to give Filipinos better access to affordable, high quality education in key cities throughout the Philippines, preparing them to be globally competitive.",
        "What is the School Mission?" to "To develop the Filipino youth into employable global professionals thru the endowment of knowledge and skills and the formation of character and spirit.",
        "What is the School Vision?" to "With distinct advantage in English Communication and Information Technology, to be the leading institution of higher learning in the region in the development of globally competitive professionals.",
        "What are the PEN Values?" to "A PEN Student is a student that lives by the PEN Values. These values form the thread that runs through all his day-to-day actions and interactions as a student. They are consistent with his responsibility to be a person with integrity, maturity, and a high sense of responsibility and community. The PEN Values are:\n- Integrity: A PEN Student's words carry weight. He is honest, trustworthy, and has consistency in words and actions.\n- Professionalism: Though not yet a professional, a PEN Student should already develop within himself a high level of professionalism. He shows decorum in his manner, dress, and speech. He respects other people, whether they agree with his ideas or not. He shows up on time. He not only does what he is told to do, he also does what needs to be done, without being told what to do.\n- Commitment: A PEN Student does not allow difficulties and failures to prevent him from reaching his goal. He perseveres towards a goal despite the obstacles around him.\n- Competence: A PEN Student has a passion for excellence. He trains to high levels of proficiency. To a PEN Student, it is always important to do a good job regardless of the field he finds himself in.\n- Teamwork: A PEN Student not only knows how to do a good job; he also knows how to work with others so that the team he is in also does a good job. In a team, a PEN Student will be ready to support or lead. He will be ready to agree or disagree. At times, he will stand firm on worthwhile principles, while at other times, he will look for a win-win situation. He knows how to work for the good of the team.\n- Innovativeness: A PEN Student is not only open to new or better ways of doing things; he is also able to find new or better ways of doing things. He is not only open to change; he is also able to initiate change that represents a new or better way of doing things.",

        // PART 2: ACADEMIC POLICIES
        "How will I know my grades?" to "Final grades will be released by the Office of the Registrar. Other term exams will be released by the faculty.",
        "What happens if I get an incomplete grade in a subject?" to "A student who gets an incomplete grade has two consecutive terms to complete it. The Dean/Director must have approved the granting of the incomplete grade. Failure to complete it within two terms results in a failing mark.",
        "Is there a fee for completing an incomplete grade?" to "The corresponding completion fee shall be applied.",
        "What is academic probation?" to "A student who fails in three subjects in a semester will be placed on probation with a maximum load of 18 units. If the student fails two subjects during probation, the maximum load is reduced to 12 units until all failed subjects are passed.",
        "How many absences can I have in a subject before I am disqualified from finals?" to "Exceeding 20% of total class meetings results in disqualification from finals or being dropped from the subject. This varies based on unit count (e.g., 8 absences for a 3-unit subject).",
        "What happens if I am late for class?" to "A student who is late for at least 10 minutes will be marked as absent.",
        "How does being tardy affect my attendance?" to "A student will incur an absence for every 3 times they are tardy.",
        "What should I do if I am absent due to sickness?" to "The student must present a written note from a parent/guardian or a certificate from an attending physician stating the nature of the sickness.",

        // PART 3: STUDENT SERVICES
        "Where can I study and do research?" to "The Library and Computer Labs serve as venues for study and research. Their collections and resources are regularly updated.",
        "Where can I get help with career planning or personal problems?" to "The Guidance and Counseling Office provides career guidance and counseling for personal or academic concerns.",
        "Does the school offer medical services?" to "The school offers medical and dental services, including consultations, minor procedures, first aid, and initial medication for common ailments.",
        "Is there any financial assistance available for students?" to "The school provides scholarships to deserving students. Consult the Center for Student Development and Leadership for details.",
        "Are students covered by personal accident insurance?" to "Students may claim insurance for personal accidents, except for high-risk activities. More details are available at the Center for Student Development and Leadership.",

        // PART 4: PEN STUDENT CODE OF CONDUCT
        "What are my rights as a student in the PHINMA Education Network?" to "Students deserve quality education that provides globally in-demand skills and fosters responsible citizenship.",
        "What are my responsibilities as a student in the PHINMA Education Network?" to "Students must uphold academic excellence, integrity, and the PEN values. Violations may result in suspension, dismissal, or expulsion.",
        "Can I be held accountable for my actions outside of school?" to "Yes, the Student Code of Conduct applies to school-sponsored activities outside the campus and actions that affect the school's reputation.",
        "What is plagiarism and what are the consequences?" to "Plagiarism results in the lowest possible score for the work and is a Category 3 offense, which may lead to dismissal or expulsion.",
        "What are the consequences of cheating on exams?" to "Cheating results in the lowest possible score for the work and is a Category 2 offense, which may lead to suspension.",
        "What are the consequences of physical assault on school grounds?" to "Physical assault resulting in injury is a Category 3 offense, leading to dismissal or expulsion.",
        "What is the penalty for stealing on school premises?" to "Stealing school, personnel, or student property is a Category 3 offense, leading to dismissal or expulsion.",
        "What are the consequences of smoking inside the campus?" to "Smoking inside the campus is a Category 1 offense, resulting in a written reprimand."
    )


    private val faqQuestions = faqList.map { it.first }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        try {
            // Initialize database
            database = ChatDatabase.getDatabase(this)
            messageDao = database.messageDao()

            initializeViews()
            setupAutoComplete()
            setupTopAppBar()
            setupRecyclerView()
            setupClickListeners()
            loadMessages()
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
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_clear -> {
                    clearChatHistory()
                    true
                }
                else -> false
            }
        }
        recyclerView = findViewById(R.id.recycler_view_messages)
        messageEditText = findViewById(R.id.edit_text_message)
        sendButton = findViewById(R.id.button_send)
    }

    private fun setupAutoComplete() {
        val adapter = object : ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, faqQuestions
        ) {
            override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                val itemView = super.getView(position, convertView, parent) as android.widget.TextView
                itemView.maxLines = 10 // Allow multi-line text
                itemView.ellipsize = null // Prevent cutting off text
                return itemView
            }
        }

        messageEditText.setAdapter(adapter)
        messageEditText.threshold = 1
        messageEditText.dropDownWidth = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(messages)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity).apply {
                stackFromEnd = true
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

    private fun loadMessages() {
        lifecycleScope.launch {
            val savedMessages = messageDao.getAllMessages()

            if (savedMessages.isEmpty()) {
                // If no messages, show welcome messages
                addWelcomeMessage()
            } else {
                // If there are saved messages, display them
                messages.addAll(savedMessages)
                chatAdapter.notifyDataSetChanged()
                recyclerView.scrollToPosition(messages.size - 1)
            }
        }
    }

    private fun addWelcomeMessage() {
        // First message - Greeting
        addBotMessage("Hello! I'm your PHINMA Education Network assistant. How can I help you today?")

        // Second message - General introduction
        messageEditText.postDelayed({
            addBotMessage("I can help you with information about PHINMA Education. Here are the main categories I can assist with:")
        }, 500)

        // Third message - Welcome and Overview (after 1000ms delay)
        messageEditText.postDelayed({
            val welcomeQuestions = listOf(
                "What is the PHINMA Education Network?",
                "What is the Mission of the PHINMA Education Network?",
                "What is the School Mission?",
                "What is the School Vision?",
                "What are the PEN Values?"
            )

            val formattedMessage = buildString {
                append("🏫 *Welcome and Overview*\n")
                append("Learn about our institution's foundation and values:\n")
                welcomeQuestions.forEach { append("• $it\n") }
            }
            addBotMessage(formattedMessage.trim())
        }, 1000)

        // Fourth message - Academic Policies (after 1500ms delay)
        messageEditText.postDelayed({
            val academicQuestions = listOf(
                "How will I know my grades?",
                "What happens if I get an incomplete grade in a subject?",
                "Is there a fee for completing an incomplete grade?",
                "What is academic probation?",
                "How many absences can I have in a subject before I am disqualified from taking the final exams?",
                "What happens if I am late for class?",
                "How does being tardy affect my attendance?",
                "What should I do if I am absent due to sickness?"
            )

            val formattedMessage = buildString {
                append("📚 *Academic Policies*\n")
                append("Questions about grades, attendance, and academic rules:\n")
                academicQuestions.forEach { append("• $it\n") }
            }
            addBotMessage(formattedMessage.trim())
        }, 1500)

        // Fifth message - Student Services (after 2000ms delay)
        messageEditText.postDelayed({
            val servicesQuestions = listOf(
                "Where can I study and do research?",
                "Where can I get help with career planning or personal problems?",
                "Does the school offer medical services?",
                "Is there any financial assistance available for students?",
                "Are students covered by personal accident insurance?"
            )

            val formattedMessage = buildString {
                append("🛎️ *Student Services*\n")
                append("Support services available to students:\n")
                servicesQuestions.forEach { append("• $it\n") }
            }
            addBotMessage(formattedMessage.trim())
        }, 2000)

        // Sixth message - Student Code of Conduct (after 2500ms delay)
        messageEditText.postDelayed({
            val conductQuestions = listOf(
                "What are my rights as a student in the PHINMA Education Network?",
                "What are my responsibilities as a student in the PHINMA Education Network?",
                "Can I be held accountable for my actions outside of school?",
                "What is plagiarism and what are the consequences?",
                "What are the consequences of cheating on exams?",
                "What are the consequences of physical assault on school grounds?",
                "What is the penalty for stealing on school premises?",
                "What are the consequences of smoking inside the campus?"
            )

            val formattedMessage = buildString {
                append("⚖️ *Student Code of Conduct*\n")
                append("Rules and behavioral expectations:\n")
                conductQuestions.forEach { append("• $it\n") }
            }
            addBotMessage(formattedMessage.trim())
        }, 2500)

        // Final message - Prompt for user (after 3000ms delay)
        messageEditText.postDelayed({
            addBotMessage("You can ask me any of these questions")
        }, 3000)
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
            ?: ("I'm sorry, but I can only answer specific questions. " +
                    "\n\n" +
                    "Please try rephrasing your query." +
                    "\n\n" +
                    "For futher assistance, please visit the on-site registrar.")
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

            // Save message to database
            lifecycleScope.launch {
                messageDao.insertMessage(message)
            }
        }
    }

    // Optional: Clear chat history if needed
    private fun clearChatHistory() {
        lifecycleScope.launch {
            messageDao.clearAllMessages()
            messages.clear()
            runOnUiThread {
                chatAdapter.notifyDataSetChanged()
                addWelcomeMessage()
            }
        }
    }
}