package com.example.ite393exam

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UrdanetaCampus : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_urdaneta)

        // College of Allied Health Sciences
        val headerAlliedHealth = findViewById<TextView>(R.id.header_allied_health)
        val contentAlliedHealth = findViewById<TextView>(R.id.content_allied_health)

        headerAlliedHealth.setOnClickListener {
            toggleVisibility(contentAlliedHealth)
        }

        // College of Management and Accountancy
        val headerManagement = findViewById<TextView>(R.id.header_management)
        val contentManagement = findViewById<TextView>(R.id.content_management)

        headerManagement.setOnClickListener {
            toggleVisibility(contentManagement)
        }
    }

    private fun toggleVisibility(content: View) {
        content.visibility = if (content.visibility == View.GONE) View.VISIBLE else View.GONE
    }
}
