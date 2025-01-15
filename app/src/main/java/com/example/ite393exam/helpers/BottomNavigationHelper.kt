package com.example.ite393exam.helpers
import android.app.Activity
import android.content.Intent
import com.example.ite393exam.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.ite393exam.R
import com.example.ite393exam.map.MapActivity
import com.example.ite393exam.modalities.ModalitiesActivity
import com.example.ite393exam.scholarships.ScholarshipPage1

/*
    HELPER CLASS SO WE CAN REUSE THIS CODE YES
 */
object BottomNavigationHelper {

    fun setupBottomNavigation(activity: Activity, bottomNavigationView: BottomNavigationView, selectedItemId: Int) {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_profile -> {
                    if (activity !is MainActivity) {
                        activity.startActivity(Intent(activity, MainActivity::class.java))
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

        // Set the selected item for the bottom navigation
        bottomNavigationView.selectedItemId = selectedItemId
        updateSelectedItem(bottomNavigationView, selectedItemId)
    }


    // YAY FOR REPEATABLE CODE HALLELUJAH
    fun updateSelectedItem(bottomNavigationView: BottomNavigationView, selectedItemId: Int) {
        // Updates the selected icon sa baba
        bottomNavigationView.selectedItemId = selectedItemId
        // ensures that button thingy stays checked according to the page
        bottomNavigationView.menu.findItem(selectedItemId)?.isChecked = true
    }
}
