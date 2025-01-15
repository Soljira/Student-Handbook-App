package com.example.ite393exam.modalities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ite393exam.R
import com.example.ite393exam.courses.CrimJusticeRadActivity
import com.example.ite393exam.courses.EducAndLibArtsActivity
import com.example.ite393exam.courses.InfoTechActivity
import com.example.ite393exam.courses.ManagementAccountancyActivity
import com.example.ite393exam.helpers.BottomNavigationHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class RemoteCoursesActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_courses)

        // Bottom Navigation Bar DO NOT TOUCH
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView, R.id.nav_modalities)

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
}
