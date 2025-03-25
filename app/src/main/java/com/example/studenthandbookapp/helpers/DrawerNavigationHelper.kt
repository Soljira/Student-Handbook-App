package com.example.studenthandbookapp.helpers

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import com.example.studenthandbookapp.ProfileActivity
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.event.EventList
import com.example.studenthandbookapp.landingpage.Login
import com.example.studenthandbookapp.map.MapActivity
import com.example.studenthandbookapp.marketing.Marketing
import com.example.studenthandbookapp.modalities.ModalitiesActivity
import com.example.studenthandbookapp.scholarships.ScholarshipPage1
import com.example.studenthandbookapp.support.Support
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

/*
    HELPER CLASS SO WE CAN REUSE THIS CODE YES
    I FUKCING LOVE REUSABLE CODE
 */
object DrawerNavigationHelper {

    fun setupDrawerNavigation(activity: Activity, drawerLayout: DrawerLayout, navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_profile -> {
                    if (activity !is ProfileActivity) {
                        activity.startActivity(Intent(activity, ProfileActivity::class.java))
                    }
                    true
                }
                R.id.nav_events -> {
                    if (activity !is EventList) {
                        activity.startActivity(Intent(activity, EventList::class.java))
                    }
                    true
                }

                R.id.nav_marketing -> {
                    if (activity !is Marketing) {
                        activity.startActivity(Intent(activity, Marketing::class.java))
                    }
                    true
                }

                R.id.nav_scholarship -> {
                    if (activity !is ScholarshipPage1) {
                        activity.startActivity(Intent(activity, ScholarshipPage1::class.java))
                    }
                    true
                }
                R.id.nav_modalities -> {
                    if (activity !is ModalitiesActivity) {
                        activity.startActivity(Intent(activity, ModalitiesActivity::class.java))
                    }
                    true
                }
//                R.id.nav_support -> {
//                    if (activity !is Support) {
//                        activity.startActivity(Intent(activity, Support::class.java))
//                    }
//                    true
//                }
                R.id.nav_logout -> {
                    val auth = FirebaseAuth.getInstance()
                    auth.signOut()  // Sign out the user
                    activity.startActivity(Intent(activity, Login::class.java))  // Redirect to the Login screen
                    activity.finish()
                    true
                }
                else -> false
            }.also {
                drawerLayout.closeDrawers() // Close the drawer after selection
            }
        }
    }
}
