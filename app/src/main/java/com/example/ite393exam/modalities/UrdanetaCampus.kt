package com.example.ite393exam.modalities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ite393exam.R
import com.example.ite393exam.courses.AlliedHealthActivity
import com.example.ite393exam.courses.CriminalJusticeActivity
import com.example.ite393exam.courses.EngineeringActivity
import com.example.ite393exam.courses.EnglishActivity
import com.example.ite393exam.courses.ManagementActivity
import com.example.ite393exam.courses.ScienceActivity
import com.example.ite393exam.helpers.BottomNavigationHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class UrdanetaCampus : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_urdaneta)

        // Bottom Navigation Bar DO NOT TOUCH
        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bottomNavigationView)
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        val menuItem = bottomNavigationView.menu.findItem(R.id.nav_modalities)
        menuItem.isChecked = true  // ensures that map button thingy stays checked


        val buttonAlliedHealth: Button = findViewById(R.id.button_allied_health)
        val buttonManagement: Button = findViewById(R.id.button_management)
        val buttonCriminal: Button = findViewById(R.id.button_criminal)
        val buttonEngineering: Button = findViewById(R.id.button_engineering)
        val buttonScience: Button = findViewById(R.id.button_science)
        val buttonEnglish: Button = findViewById(R.id.button_english)

        buttonAlliedHealth.setOnClickListener {
            navigateToActivity(AlliedHealthActivity::class.java)
        }

        buttonManagement.setOnClickListener {
            navigateToActivity(ManagementActivity::class.java)
        }

        buttonCriminal.setOnClickListener {
            navigateToActivity(CriminalJusticeActivity::class.java)
        }

        buttonEngineering.setOnClickListener {
            navigateToActivity(EngineeringActivity::class.java)
        }

        buttonScience.setOnClickListener {
            navigateToActivity(ScienceActivity::class.java)
        }

        buttonEnglish.setOnClickListener {
            navigateToActivity(EnglishActivity::class.java)
        }
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}
