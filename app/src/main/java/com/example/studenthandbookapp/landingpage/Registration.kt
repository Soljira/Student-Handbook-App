package com.example.studenthandbookapp.landingpage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.EditProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Registration : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var registerButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var fullNameEditText: EditText
    private lateinit var studentNumberEditText: EditText
    private lateinit var goToLoginTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        registerButton = findViewById(R.id.btnRegister)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        fullNameEditText = findViewById(R.id.fullNameEditText)
        studentNumberEditText = findViewById(R.id.studentNumberEditText)
        goToLoginTextView = findViewById(R.id.tvGoToLogin) // Add this line
    }

    private fun setupClickListeners() {
        registerButton.setOnClickListener {
            registerUser()
        }
        goToLoginTextView.setOnClickListener {
            finish()
        }
    }

    private fun registerUser() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()
        val fullName = fullNameEditText.text.toString().trim()
        val studentNumber = studentNumberEditText.text.toString().trim()

        when {
            email.isEmpty() -> showToast("Please enter email")
            password.isEmpty() -> showToast("Please enter password")
            password != confirmPassword -> showToast("Passwords don't match")
            password.length < 6 -> showToast("Password must be at least 6 characters")
            fullName.isEmpty() -> showToast("Please enter your full name")
            studentNumber.isEmpty() -> showToast("Please enter your student number")
            else -> createFirebaseUser(email, password, fullName, studentNumber)
        }
    }

    private fun createFirebaseUser(email: String, password: String, fullName: String, studentNumber: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: ""
                    saveUserToFirestore(userId, email, fullName, studentNumber)
                    showToast("Registration successful")
                    startActivity(Intent(this, EditProfile::class.java))
                    finish()
                } else {
                    showToast("Registration failed: ${task.exception?.message}")
                }
            }
    }

    private fun saveUserToFirestore(userId: String, email: String, fullName: String, studentNumber: String) {
        val user = hashMapOf(
            "email" to email,
            "fullName" to fullName,
            "studentNumber" to studentNumber,
            "profileImageUrl" to "" // Initialize with empty, can be updated later
        )

        db.collection("users").document(userId)
            .set(user)
            .addOnFailureListener { e ->
                showToast("Error saving user details: ${e.message}")
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}