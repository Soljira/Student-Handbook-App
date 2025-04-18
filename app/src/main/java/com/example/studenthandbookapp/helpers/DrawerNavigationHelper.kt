@file:Suppress("DEPRECATION")

package com.example.studenthandbookapp.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.studenthandbookapp.EditProfile
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.calendar.CalendarActivity
import com.example.studenthandbookapp.event.EventListActivity
import com.example.studenthandbookapp.landingpage.Login
import com.example.studenthandbookapp.marketing.Marketing
import com.example.studenthandbookapp.chat.ChatActivity
import com.example.studenthandbookapp.home.Home
import com.example.studenthandbookapp.manual.Manual
import com.example.studenthandbookapp.map.MapActivity
import com.example.studenthandbookapp.modalities.ModalitiesActivity
import com.example.studenthandbookapp.scholarships.ScholarshipPage1
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object DrawerNavigationHelper {
    private val auth = FirebaseAuth.getInstance()
    @SuppressLint("StaticFieldLeak")
    private val db = FirebaseFirestore.getInstance()
    private const val PREFS_NAME = "LoginPrefs"
    private const val PREF_IS_GUEST = "is_guest"

    fun setupDrawerNavigation(
        activity: Activity,
        drawerLayout: DrawerLayout,
        navigationView: NavigationView
    ) {
        // Setup header
        setupProfileHeader(activity, navigationView)

        // Setup navigation items
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
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
                R.id.nav_events -> {
                    if (activity !is EventListActivity) {
                        activity.startActivity(Intent(activity, EventListActivity::class.java))
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
                R.id.nav_support -> {
                    if (activity !is ChatActivity) {
                        activity.startActivity(Intent(activity, ChatActivity::class.java))
                    }
                    true
                }
                R.id.nav_settings -> {
                        if (activity !is EditProfile) {
                            activity.startActivity(Intent(activity, EditProfile::class.java))
                        }
                        true
                    }
                R.id.nav_logout -> {
                    logoutUser(activity)
                    true
                }
                else -> false
            }.also {
                drawerLayout.closeDrawers()
            }
        }
    }

    private fun setupProfileHeader(activity: Activity, navigationView: NavigationView) {
        val headerView = navigationView.getHeaderView(0)
        val profileImageHeader = headerView.findViewById<ImageView>(R.id.profileImageHeader)
        val usernameHeader = headerView.findViewById<TextView>(R.id.usernameHeader)
        val emailHeader = headerView.findViewById<TextView>(R.id.emailHeader)

        if (isGuestUser(activity)) {
            // Set guest user information
            usernameHeader.text = activity.getString(R.string.guest_user)
            emailHeader.text = activity.getString(R.string.guest_email)
            profileImageHeader.setImageResource(R.drawable.ic_profile_placeholder)
        } else {
            // Regular user - load from Firebase
            val currentUser = auth.currentUser
            currentUser?.let { user ->
                emailHeader.text = user.email ?: ""

                db.collection("users").document(user.uid)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            usernameHeader.text = document.getString("fullName") ?: "User"

                            document.getString("profileImageUrl")?.takeIf { it.isNotEmpty() }?.let { imageUrl ->
                                Glide.with(activity)
                                    .load(imageUrl)
                                    .circleCrop()
                                    .placeholder(R.drawable.ic_profile_placeholder)
                                    .into(profileImageHeader)
                            }
                        }
                    }
            }
        }
    }

    private fun logoutUser(activity: Activity) {
        val prefs = activity.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val editor = prefs.edit()
        editor.remove(PREF_IS_GUEST)
        editor.apply()

        auth.signOut()
        activity.startActivity(Intent(activity, Login::class.java))
        activity.finish()
    }

    private fun isGuestUser(activity: Activity): Boolean {
        val prefs = activity.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        return prefs.getBoolean(PREF_IS_GUEST, false)
    }
}