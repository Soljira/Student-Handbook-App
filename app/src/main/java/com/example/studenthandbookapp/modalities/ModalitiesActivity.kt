package com.example.studenthandbookapp.modalities

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.helpers.BottomNavigationHelper
import com.example.studenthandbookapp.helpers.BottomNavigationHelper.unselectBottomNavIcon
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.helpers.TopAppBarHelper
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class ModalitiesActivity : AppCompatActivity() {
    lateinit var linearLayout: LinearLayout
    lateinit var flFragmentContainer: FrameLayout

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var topAppBar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO LAGYAN NG MGA TAO PICTURES UNG MGA COURSES
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modalities)
        initializeNavigationStuff()

        // Bottom Navigation Bar DO NOT TOUCH
//        bottomNavigationView = findViewById(R.id.bottomNavigationView)
//        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
//        bottomNavigationView.selectedItemId = R.id.nav_modalities

        val btnFlex24 = findViewById<ImageButton>(R.id.btnFlex24)
        val btnFlexRemote = findViewById<ImageButton>(R.id.btnFlexRemote)

        linearLayout = findViewById<LinearLayout>(R.id.linearlayout)
        flFragmentContainer = findViewById(R.id.flFragmentContainer)


        // Fragment Initializations
        val flex24Fragment = Flex24Fragment()
        val flexRemoteFragment = FlexRemoteFragment()

        btnFlex24.setOnClickListener {
            fragmentBeginTransaction(flex24Fragment)

        }

        btnFlexRemote.setOnClickListener {
            fragmentBeginTransaction(flexRemoteFragment)
        }

        // TODO: Copy what I've done in activity_school_selection.xml in line 12-14 instead of using supportFragmentManager.addOnBackStackChangedListener
        // Fixes my stupid code below that hides the linear layout when the user opens a new fragment
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                // Show the linear layout when the back stack is empty
                showOtherComponents()
            }
        }

    }

    // Wrote this function to make my code more readable
    fun fragmentBeginTransaction(fragment: Fragment) {
        hideOtherComponents()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragmentContainer, fragment)
            addToBackStack(null)
            commit()  // to actually apply changes
        }
    }

    fun hideOtherComponents() {
        linearLayout.visibility = View.GONE
    }

    fun showOtherComponents() {
        linearLayout.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
//        bottomNavigationView.selectedItemId = R.id.nav_modalities
        unselectBottomNavIcon(bottomNavigationView)
    }

    fun initializeNavigationStuff() {
        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigationView = findViewById(R.id.navigation_view)
        topAppBar = findViewById(R.id.topAppBar)

        TopAppBarHelper.setupTopAppBar(this, topAppBar, drawerLayout, "Modalities")
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        DrawerNavigationHelper.setupDrawerNavigation(this, drawerLayout, navigationView)

        unselectBottomNavIcon(bottomNavigationView)
    }
}