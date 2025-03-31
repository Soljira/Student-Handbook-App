package com.example.studenthandbookapp.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.studenthandbookapp.R
import com.google.android.material.appbar.MaterialToolbar

class MapMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_menu)

        setupTopAppBar()
        setupFloorButtons()
    }

    private fun setupTopAppBar() {
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(topAppBar)

        // Enable back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        topAppBar.setNavigationOnClickListener {
            finish() // Handle back button press
        }
    }

    private fun setupFloorButtons() {
        // Initialize fragments
        val groundFloorFragment = GroundFloorFragment()
        val secondFloorFragment = SecondFloorFragment()
        val thirdFloorFragment = ThirdFloorFragment()
        val fourthFloorFragment = FourthFloorFragment()
        val fifthFloorFragment = FifthFloorFragment()

        // Set click listeners
        findViewById<ImageButton>(R.id.btnGroundFloor).setOnClickListener {
            replaceFragment(groundFloorFragment)
        }
        findViewById<ImageButton>(R.id.btnSecondFloor).setOnClickListener {
            replaceFragment(secondFloorFragment)
        }
        findViewById<ImageButton>(R.id.btnThirdFloor).setOnClickListener {
            replaceFragment(thirdFloorFragment)
        }
        findViewById<ImageButton>(R.id.btnFourthFloor).setOnClickListener {
            replaceFragment(fourthFloorFragment)
        }
        findViewById<ImageButton>(R.id.btnFifthFloor).setOnClickListener {
            replaceFragment(fifthFloorFragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}