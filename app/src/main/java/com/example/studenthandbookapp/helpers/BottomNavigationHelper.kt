package com.example.studenthandbookapp.helpers
import android.app.Activity
import android.content.Intent
import com.example.studenthandbookapp.ProfileActivity
import com.example.studenthandbookapp.R
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
                R.id.nav_profile -> {
                    if (activity !is ProfileActivity) {
                        activity.startActivity(Intent(activity, ProfileActivity::class.java))
                    }
                    true
                }
                R.id.nav_map -> {
                    if (activity !is MapActivity) {
                        activity.startActivity(Intent(activity, MapActivity::class.java))
                    }
                    true
                }
                R.id.nav_modalities -> {
                    if (activity !is ModalitiesActivity) {
                        activity.startActivity(Intent(activity, ModalitiesActivity::class.java))
                    }
                    true
                }
                R.id.nav_scholarship -> {
                    if (activity !is ScholarshipPage1) {
                        activity.startActivity(Intent(activity, ScholarshipPage1::class.java))
                    }
                    true
                }
                else -> false
            }
        }
    }
}
