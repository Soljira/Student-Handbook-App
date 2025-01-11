package com.example.ite393exam

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var isEditable = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nameField = findViewById<EditText>(R.id.editTextText)
        val campusField = findViewById<EditText>(R.id.editTextText2)
        val courseField = findViewById<EditText>(R.id.editTextText3)
        val emailField = findViewById<EditText>(R.id.editTextText4)

        val editButton = findViewById<ImageButton>(R.id.imageButton5)

        editButton.setOnClickListener {
            isEditable = !isEditable
            nameField.isEnabled = isEditable
            campusField.isEnabled = isEditable
            courseField.isEnabled = isEditable
            emailField.isEnabled = isEditable
            if (isEditable) {
                editButton.setImageResource(R.drawable.ch)
            } else {
                editButton.setImageResource(R.drawable.edit)
            }
        }
    }
}