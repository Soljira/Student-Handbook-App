package com.example.ite393exam.scholarships

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ite393exam.R
import com.example.ite393exam.helpers.BottomNavigationHelper
import com.google.android.material.bottomnavigation.BottomNavigationView


class ScholarshipPage1 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set content view first
        setContentView(R.layout.activity_scholarship_page1)

        // Bottom Navigation Bar DO NOT TOUCH
        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bottomNavigationView)
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.nav_scholarship

        val btnScholarshipLists: ImageButton = findViewById(R.id.btn_scholarship_lists)
        btnScholarshipLists.setOnClickListener {
            val intent = Intent(this, ScholarshipPage2::class.java)
            startActivity(intent)
        }

        // Enabling edge-to-edge after setting the content view
        enableEdgeToEdge()

    }
}
