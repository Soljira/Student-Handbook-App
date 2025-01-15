package com.example.ite393exam

import android.content.Intent
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ite393exam.helpers.BottomNavigationHelper
import com.example.ite393exam.map.MapActivity
import com.example.ite393exam.map.MapMenuActivity
import com.example.ite393exam.scholarships.ScholarshipPage1
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {
    private var isEditable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //test
        // Bottom Navigation Bar DO NOT TOUCH
        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bottomNavigationView)
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.nav_profile

        val nameField = findViewById<EditText>(R.id.editTextText)
        val emailField = findViewById<EditText>(R.id.editTextText4)
        val phoneField = findViewById<EditText>(R.id.editTextText5)
        val birthdateField = findViewById<EditText>(R.id.editTextBirthdate)
//        val campusSpinner = findViewById<Spinner>(R.id.spinnerCampus)
        val courseSpinner = findViewById<Spinner>(R.id.spinnerCourse)
        val editButton = findViewById<ImageButton>(R.id.imageButton5)

        ArrayAdapter.createFromResource(
            this,
            R.array.campus_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            campusSpinner.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            this,
            R.array.course_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            courseSpinner.adapter = adapter

            // pre-selects leeian's course
            courseSpinner.setSelection(3)
        }
//        campusSpinner.isEnabled = false
        courseSpinner.isEnabled = false
        birthdateField.isEnabled = false


        editButton.setOnClickListener {
            isEditable = !isEditable

            nameField.isEnabled = isEditable
            emailField.isEnabled = isEditable
            phoneField.isEnabled = isEditable
            // WALA KASI NASA LANDING PAGE PALA UNG PAGPILI NG SCHOOL SORRY
//            campusSpinner.isEnabled = isEditable
            courseSpinner.isEnabled = isEditable
            birthdateField.isEnabled = isEditable

            editButton.setImageResource(if (isEditable) R.drawable.chv2 else R.drawable.editv3)
        }
        birthdateField.setOnClickListener {
            if (isEditable) {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(
                    this,
                    { _, selectedYear, selectedMonth, selectedDay ->

                        val formattedDate = "${selectedMonth + 1}/$selectedDay/$selectedYear"
                        birthdateField.setText(formattedDate)
                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.show()
            }
        }
//        campusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {}
//        }
        courseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}
