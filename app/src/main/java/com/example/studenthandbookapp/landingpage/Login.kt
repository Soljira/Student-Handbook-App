package com.example.studenthandbookapp.landingpage

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.home.Home
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var rememberMeCheckbox: CheckBox

    private lateinit var sharedPreferences: SharedPreferences
    private val PREFS_NAME = "LoginPrefs"
    private val PREF_EMAIL = "email"
    private val PREF_PASSWORD = "password"
    private val PREF_REMEMBER = "remember"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Check if user is already logged in
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, Home::class.java))
            finish()
            return
        }

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        // Initialize views
        loginButton = findViewById(R.id.btnLogin)
        registerButton = findViewById(R.id.btnRegister)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox)

        // Load saved credentials if "Remember Me" was checked
        val savedEmail = sharedPreferences.getString(PREF_EMAIL, "")
        val savedPassword = sharedPreferences.getString(PREF_PASSWORD, "")
        val rememberMe = sharedPreferences.getBoolean(PREF_REMEMBER, false)

        if (rememberMe) {
            emailEditText.setText(savedEmail)
            passwordEditText.setText(savedPassword)
            rememberMeCheckbox.isChecked = true
        }

        // Set up login button click listener
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val remember = rememberMeCheckbox.isChecked

            when {
                email.isEmpty() -> showToast("Please enter email")
                password.isEmpty() -> showToast("Please enter password")
                else -> {
                    // Save credentials if "Remember Me" is checked
                    val editor = sharedPreferences.edit()
                    if (remember) {
                        editor.putString(PREF_EMAIL, email)
                        editor.putString(PREF_PASSWORD, password)
                        editor.putBoolean(PREF_REMEMBER, true)
                    } else {
                        editor.clear()
                    }
                    editor.apply()

                    performLogin(email, password)
                }
            }
        }

        // Set up register button click listener
        registerButton.setOnClickListener {
            startActivity(Intent(this, Registration::class.java))
        }
    }

    private fun performLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, Home::class.java))
                    finish()
                } else {
                    showToast("Login failed: ${task.exception?.message}")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}