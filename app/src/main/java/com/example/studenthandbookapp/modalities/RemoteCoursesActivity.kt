package com.example.studenthandbookapp.modalities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.courses.CrimJusticeRadActivity
import com.example.studenthandbookapp.courses.EducAndLibArtsActivity
import com.example.studenthandbookapp.courses.InfoTechActivity
import com.example.studenthandbookapp.courses.ManagementAccountancyActivity
import com.example.studenthandbookapp.helpers.BottomNavigationHelper
import com.example.studenthandbookapp.helpers.BottomNavigationHelper.unselectBottomNavIcon
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.helpers.TopAppBarHelper
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class RemoteCoursesActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var topAppBar: MaterialToolbar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modalities_list_remote)
        initializeNavigationStuff()

        val menuItem = bottomNavigationView.menu.findItem(R.id.nav_modalities)
        menuItem?.isChecked = true  // ensures that map button thingy stays checked

        val buttonManageAcc: Button = findViewById(R.id.button_management_Acc)
        val buttonCriminalJustice: Button = findViewById(R.id.button_bs_crim)
        val buttonInfoTech: Button = findViewById(R.id.button_bs_it)
        val buttonCela: Button = findViewById(R.id.button_bs_educ_liberal_arts)

        buttonManageAcc.setOnClickListener{
            navigateToActivity(ManagementAccountancyActivity::class.java)
        }

        buttonCriminalJustice.setOnClickListener {
            navigateToActivity(CrimJusticeRadActivity::class.java)
        }

        buttonInfoTech.setOnClickListener{
            navigateToActivity(InfoTechActivity::class.java)
        }

        buttonCela.setOnClickListener{
            navigateToActivity(EducAndLibArtsActivity::class.java)
        }
    }
    private fun navigateToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }


    fun initializeNavigationStuff() {
        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigationView = findViewById(R.id.navigation_view)
        topAppBar = findViewById(R.id.topAppBar)

        TopAppBarHelper.setupTopAppBar(this, topAppBar, drawerLayout, "Modalities")
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        DrawerNavigationHelper.setupDrawerNavigation(this, drawerLayout, navigationView)

//        bottomNavigationView.selectedItemId = R.id.nav_home
        unselectBottomNavIcon(bottomNavigationView)
    }

}
