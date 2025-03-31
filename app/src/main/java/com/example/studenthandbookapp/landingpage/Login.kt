package com.example.studenthandbookapp.landingpage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Temporarily bypass login para hnd paulit ulit naglologin (habang wala pa firebase auth)
//        startActivity(Intent(this, Home::class.java))
//        startActivity(Intent(this, EventDetails::class.java))
//        startActivity(Intent(this, EventList::class.java))
//        startActivity(Intent(this, AddUserEvent::class.java))
//        startActivity(Intent(this, Manual::class.java))

        // Check if the user is already signed in
        auth = FirebaseAuth.getInstance()

        /**
         * Persistent session functionality
         */
        // If the user is already logged in, redirect to Home activity
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // If logged in, navigate directly to Home activity
            startActivity(Intent(this, Home::class.java))
            finish()
        }

        loginButton = findViewById(R.id.btnLogin)
        registerButton = findViewById(R.id.btnRegister)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            when {
                email.isEmpty() -> showToast("Please enter email")
                password.isEmpty() -> showToast("Please enter password")
                else -> performLogin(email, password)
            }
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
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
