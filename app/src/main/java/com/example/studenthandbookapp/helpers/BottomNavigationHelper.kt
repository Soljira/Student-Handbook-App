package com.example.studenthandbookapp.helpers
import android.app.Activity
import android.content.Intent
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.calendar.CalendarActivity
import com.example.studenthandbookapp.home.Home
import com.example.studenthandbookapp.manual.Manual
import com.example.studenthandbookapp.map.MapActivity
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

    // Purpose: para hindi macheck ung icons sa bottom nav bar kapag nagnavigate thru side drawer
    fun unselectBottomNavIcon(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.menu.setGroupCheckable(0, true, false)
        for (i in 0 until bottomNavigationView.menu.size()) { // PARA HINDI LANG NAV_HOME UNG UNCHECKED
            bottomNavigationView.menu.getItem(i).isChecked = false
        }
        bottomNavigationView.menu.setGroupCheckable(0, true, true)
    }
}
