package com.example.studenthandbookapp.helpers

import android.app.Activity
import android.content.Intent
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.studenthandbookapp.EditProfile
import com.example.studenthandbookapp.R
import com.google.android.material.appbar.MaterialToolbar


// i fucking love reusalbe code
// save me britanny spears
// kill me
// pakyu kill me
object TopAppBarHelper {

    fun setupTopAppBar(
        activity: Activity,
        topAppBar: MaterialToolbar,
        drawerLayout: DrawerLayout,
        title: String = "Home"
    ) {
        topAppBar.title = title

        // PAG CLINICK BUBUKAS DRAWER OKEY?????????????
        topAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_profile -> {
                    if (activity !is EditProfile) {
                        activity.startActivity(Intent(activity, EditProfile::class.java))
                    }
                    true
                }
                else -> false
            }
        }
    }
}