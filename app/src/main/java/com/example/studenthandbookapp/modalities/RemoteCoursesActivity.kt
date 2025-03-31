package com.example.studenthandbookapp.modalities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.courses.CrimJusticeRadActivity
import com.example.studenthandbookapp.courses.EducAndLibArtsActivity
import com.example.studenthandbookapp.courses.InfoTechActivity
import com.example.studenthandbookapp.courses.ManagementAccountancyActivity
import com.google.android.material.appbar.MaterialToolbar

class RemoteCoursesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modalities_list_remote)

        setupTopAppBar()
        setupButtonListeners()
    }

    private fun setupTopAppBar() {
        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener {
            finish() // Simply finish the activity when back is pressed
        }
    }

    private fun setupButtonListeners() {
        findViewById<Button>(R.id.button_management_Acc).setOnClickListener {
            startActivity(Intent(this, ManagementAccountancyActivity::class.java))
        }

        findViewById<Button>(R.id.button_bs_crim).setOnClickListener {
            startActivity(Intent(this, CrimJusticeRadActivity::class.java))
        }

        findViewById<Button>(R.id.button_bs_it).setOnClickListener {
            startActivity(Intent(this, InfoTechActivity::class.java))
        }

        findViewById<Button>(R.id.button_bs_educ_liberal_arts).setOnClickListener {
            startActivity(Intent(this, EducAndLibArtsActivity::class.java))
        }
    }

    override fun onDestroy() {
        // Clear any references that might cause leaks
        super.onDestroy()
    }
}