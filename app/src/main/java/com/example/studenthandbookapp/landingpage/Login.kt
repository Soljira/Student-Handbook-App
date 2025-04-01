package com.example.studenthandbookapp.landingpage

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.home.Home
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var guestButton: TextView
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var rememberMeCheckbox: CheckBox

    private lateinit var sharedPreferences: SharedPreferences
    private val PREFS_NAME = "LoginPrefs"
    private val PREF_EMAIL = "email"
    private val PREF_PASSWORD = "password"
    private val PREF_REMEMBER = "remember"
    private val PREF_IS_GUEST = "is_guest"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Go back to SchoolSelection when back is pressed
                startActivity(Intent(this@Login, SchoolSelection::class.java))
                finish()
            }
        })

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Check if user is already logged in or was a guest
        val currentUser = auth.currentUser
        val isGuest = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getBoolean(PREF_IS_GUEST, false)

        if (currentUser != null || isGuest) {
            startActivity(Intent(this, Home::class.java))
            finish()
            return
        }

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        // Initialize views
        loginButton = findViewById(R.id.btnLogin)
        registerButton = findViewById(R.id.btnRegister)
        guestButton = findViewById(R.id.btnGuest) // Make sure to add this button in your layout
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
                        editor.remove(PREF_EMAIL)
                        editor.remove(PREF_PASSWORD)
                        editor.putBoolean(PREF_REMEMBER, false)
                    }
                    editor.putBoolean(PREF_IS_GUEST, false) // Mark as not guest
                    editor.apply()

                    performLogin(email, password)
                }
            }
        }

        // Set up register button click listener
        registerButton.setOnClickListener {
            startActivity(Intent(this, Registration::class.java))
        }

        // Set up guest button click listener
        guestButton.setOnClickListener {
            // Mark as guest user in SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putBoolean(PREF_IS_GUEST, true)
            editor.remove(PREF_EMAIL)
            editor.remove(PREF_PASSWORD)
            editor.remove(PREF_REMEMBER)
            editor.apply()

            // Proceed to Home activity without Firebase authentication
            startActivity(Intent(this, Home::class.java))
            finish()
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