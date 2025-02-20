package com.example.studenthandbookapp.helpers
import android.app.Activity
import android.content.Intent
import com.example.studenthandbookapp.ProfileActivity
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.calendar.CalendarActivity
import com.example.studenthandbookapp.home.Home
import com.example.studenthandbookapp.manual.Manual
import com.example.studenthandbookapp.map.MapActivity
import com.example.studenthandbookapp.modalities.ModalitiesActivity
import com.example.studenthandbookapp.scholarships.ScholarshipPage1
import com.google.android.material.bottomnavigation.BottomNavigationView

/*
    HELPER CLASS SO WE CAN REUSE THIS CODE YES
 */
object BottomNavigationHelper {

    fun setupBottomNavigation(activity: Activity, bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    if (activity !is Home) {
                        activity.startActivity(Intent(activity, Home::class.java))
                    }
                    true
                }
                R.id.nav_map -> {
                    if (activity !is MapActivity) {
                        activity.startActivity(Intent(activity, MapActivity::class.java))
                    }
                    true
                }
                R.id.nav_calendar -> {
                    if (activity !is CalendarActivity) {
                        activity.startActivity(Intent(activity, CalendarActivity::class.java))
                    }
                    true
                }
                R.id.nav_manual -> {
                    if (activity !is Manual) {
                        activity.startActivity(Intent(activity, Manual::class.java))
                    }
                    true
                }
                else -> false
            }
        }
    }
}
