package com.example.studenthandbookapp.landingpage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.event.AddUserEvent
import com.example.studenthandbookapp.event.EventDetails
import com.example.studenthandbookapp.event.EventList
import com.example.studenthandbookapp.home.Home
import com.example.studenthandbookapp.manual.Manual

class Login : AppCompatActivity() {
    lateinit var loginButton: Button
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Temporarily bypass login para hnd paulit ulit naglologin (habang wala pa firebase auth)
//        startActivity(Intent(this, Home::class.java))
//        startActivity(Intent(this, EventDetails::class.java))
        startActivity(Intent(this, EventList::class.java))
//        startActivity(Intent(this, AddUserEvent::class.java))
//        startActivity(Intent(this, Manual::class.java))


        loginButton = findViewById<Button>(R.id.btnLogin)
        emailEditText = findViewById<EditText>(R.id.emailEditText)
        passwordEditText = findViewById<EditText>(R.id.passwordEditText)

        // TODO: Firebase Authentication
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // ERROR HANDLING/VALIDATION CHECKS TO CHECK IF EMAIL AND PASSWORD IS EMPTY
            when {
                email.isEmpty() -> showToast("Please enter email")
                password.isEmpty() -> showToast("Please enter password")
                else -> performLogin(email, password)
            }
        }  // End of setOnClickListener
    }  // End of onCreate method


    fun performLogin(email: String, password: String) {
        if (email == "admin@phinma.com" && password == "password123") {
            startActivity(Intent(this, SchoolSelection::class.java))
            finish()
        } else {
            Toast.makeText(
                this,
                "Invalid credentials. Please try again.",
                Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}  // End of Login class