package com.example.studenthandbookapp.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.helpers.BottomNavigationHelper
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.helpers.TopAppBarHelper
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MapMenuActivity : AppCompatActivity() {

    lateinit var btnGroundFloor : ImageButton
    lateinit var btnSecondFloor : ImageButton
    lateinit var btnThirdFloor : ImageButton
    lateinit var btnFourthFloor : ImageButton
    lateinit var btnFifthFloor : ImageButton

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var topAppBar: MaterialToolbar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_menu)
        initializeNavigationStuff()

//        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bottomNavigationView)
//        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        val menuItem = bottomNavigationView.menu.findItem(R.id.nav_map)
        menuItem.isChecked = true  // ensures that map button thingy stays checked

        btnGroundFloor = findViewById(R.id.btnGroundFloor)
        btnSecondFloor = findViewById(R.id.btnSecondFloor)
        btnThirdFloor = findViewById(R.id.btnThirdFloor)
        btnFourthFloor = findViewById(R.id.btnFourthFloor)
        btnFifthFloor = findViewById(R.id.btnFifthFloor)

        // Fragment Initializations
        val groundFloorFragment = GroundFloorFragment()
        val secondFloorFragment = SecondFloorFragment()
        val thirdFloorFragment = ThirdFloorFragment()
        val fourthFloorFragment = FourthFloorFragment()
        val fifthFloorFragment = FifthFloorFragment()

        btnGroundFloor.setOnClickListener {
            fragmentBeginTransaction(groundFloorFragment)
        }
        btnSecondFloor.setOnClickListener {
            fragmentBeginTransaction(secondFloorFragment)
        }
        btnThirdFloor.setOnClickListener {
            fragmentBeginTransaction(thirdFloorFragment)
        }
        btnFourthFloor.setOnClickListener {
            fragmentBeginTransaction(fourthFloorFragment)
        }
        btnFifthFloor.setOnClickListener {
            fragmentBeginTransaction(fifthFloorFragment)
        }


    }


    // Wrote this function to make my code more readable
    fun fragmentBeginTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragmentContainer, fragment)
            addToBackStack(null)
            commit()  // to actually apply changes
        }
    }


    fun initializeNavigationStuff() {
        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigationView = findViewById(R.id.navigation_view)
        topAppBar = findViewById(R.id.topAppBar)

        TopAppBarHelper.setupTopAppBar(this, topAppBar, drawerLayout, "Map")
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        DrawerNavigationHelper.setupDrawerNavigation(this, drawerLayout, navigationView)

//        bottomNavigationView.selectedItemId = R.id.nav_map
    }

}