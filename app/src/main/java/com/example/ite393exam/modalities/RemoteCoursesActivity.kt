package com.example.ite393exam.modalities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ite393exam.R
import com.example.ite393exam.helpers.BottomNavigationHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class RemoteCoursesActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_courses)

        // Bottom Navigation Bar DO NOT TOUCH
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        val menuItem = bottomNavigationView.menu.findItem(R.id.nav_modalities)
        menuItem.isChecked = true  // ensures that map button thingy stays checked



    }
}
