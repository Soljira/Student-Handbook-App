package com.example.studenthandbookapp.scholarships

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.studenthandbookapp.R
import com.google.android.material.appbar.MaterialToolbar

class ScholarshipPage1 : AppCompatActivity() {
    private lateinit var topAppBar: MaterialToolbar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scholarship_page1)

        // Initialize only the top app bar
        topAppBar = findViewById(R.id.topAppBar)
        setupTopAppBar()

        val btnScholarshipLists: ImageButton = findViewById(R.id.btn_scholarship_lists)
        btnScholarshipLists.setOnClickListener {
            val intent = Intent(this, ScholarshipPage2::class.java)
            startActivity(intent)
        }
    }

    private fun setupTopAppBar() {
        topAppBar.title = "Scholarships"
        topAppBar.setNavigationOnClickListener {
            finish() // Just close the activity when navigation icon is clicked
        }
    }
}