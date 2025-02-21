package com.example.studenthandbookapp.modalities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.courses.AlliedHealthActivity
import com.example.studenthandbookapp.courses.CriminalJusticeActivity
import com.example.studenthandbookapp.courses.EngineeringActivity
import com.example.studenthandbookapp.courses.EnglishActivity
import com.example.studenthandbookapp.courses.ManagementActivity
import com.example.studenthandbookapp.courses.ScienceActivity
import com.example.studenthandbookapp.helpers.BottomNavigationHelper
import com.example.studenthandbookapp.helpers.BottomNavigationHelper.unselectBottomNavIcon
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.helpers.TopAppBarHelper
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class UrdanetaCampus : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var topAppBar: MaterialToolbar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_urdaneta)
        initializeNavigationStuff()
        val menuItem = bottomNavigationView.menu.findItem(R.id.nav_modalities)
        menuItem?.isChecked = true  // ensures that map button thingy stays checked


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

    fun initializeNavigationStuff() {
        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigationView = findViewById(R.id.navigation_view)
        topAppBar = findViewById(R.id.topAppBar)

        TopAppBarHelper.setupTopAppBar(this, topAppBar, drawerLayout, "Modalities")
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        DrawerNavigationHelper.setupDrawerNavigation(this, drawerLayout, navigationView)

//        bottomNavigationView.selectedItemId = R.id.nav_home
        unselectBottomNavIcon(bottomNavigationView)
    }
}
