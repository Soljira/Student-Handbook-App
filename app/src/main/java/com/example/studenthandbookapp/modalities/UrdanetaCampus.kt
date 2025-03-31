package com.example.studenthandbookapp.modalities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.courses.AlliedHealthActivity
import com.example.studenthandbookapp.courses.CriminalJusticeActivity
import com.example.studenthandbookapp.courses.EngineeringActivity
import com.example.studenthandbookapp.courses.EnglishActivity
import com.example.studenthandbookapp.courses.ManagementActivity
import com.example.studenthandbookapp.courses.ScienceActivity
import com.google.android.material.appbar.MaterialToolbar

class UrdanetaCampus : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modalities_list_urdaneta_flex24)

        setupTopAppBar()
        setupButtonListeners()
    }

    private fun setupTopAppBar() {
        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener {
            finish() // Close the activity when back button is pressed
        }
    }

    private fun setupButtonListeners() {
        findViewById<Button>(R.id.button_allied_health).setOnClickListener {
            startActivity(Intent(this, AlliedHealthActivity::class.java))
        }

        findViewById<Button>(R.id.button_management).setOnClickListener {
            startActivity(Intent(this, ManagementActivity::class.java))
        }

        findViewById<Button>(R.id.button_criminal).setOnClickListener {
            startActivity(Intent(this, CriminalJusticeActivity::class.java))
        }

        findViewById<Button>(R.id.button_engineering).setOnClickListener {
            startActivity(Intent(this, EngineeringActivity::class.java))
        }

        findViewById<Button>(R.id.button_science).setOnClickListener {
            startActivity(Intent(this, ScienceActivity::class.java))
        }

        findViewById<Button>(R.id.button_english).setOnClickListener {
            startActivity(Intent(this, EnglishActivity::class.java))
        }
    }
}