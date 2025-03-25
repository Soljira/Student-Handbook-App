package com.example.studenthandbookapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.studenthandbookapp.helpers.BottomNavigationHelper
import com.example.studenthandbookapp.helpers.BottomNavigationHelper.unselectBottomNavIcon
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.helpers.TopAppBarHelper
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.redmadrobot.inputmask.MaskedTextChangedListener
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private var isEditable = false
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var topAppBar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initializeNavigationStuff()



        val nameField = findViewById<EditText>(R.id.editTextText)
        val emailField = findViewById<EditText>(R.id.editTextText4)
        val phoneField = findViewById<EditText>(R.id.editTextText5)
        val birthdateField = findViewById<EditText>(R.id.editTextBirthdate)
        val campusSpinner = findViewById<Spinner>(R.id.spinnerCampus)
        val courseSpinner = findViewById<Spinner>(R.id.spinnerCourse)
        val editButton = findViewById<ImageButton>(R.id.imageButton5)

        var previousValidSelection = 0 // tracks the previous valid item for campusSpinner
        // ^ useful for determining which schools are available in the app. For now it's just upang urdaneta


        // Refer to https://github.com/RedMadRobot/input-mask-android/wiki/Quick-Start
        // Adds an input mask to phone field
        // android:digits should have a space character, otherwise the spacing won't work
        val listener = MaskedTextChangedListener("09[00] [000] [0000]", phoneField)
        phoneField.addTextChangedListener(listener)
        phoneField.onFocusChangeListener = listener

        ArrayAdapter.createFromResource(
            this,
            R.array.campus_array,
            R.layout.custom_spinner_selected_item  // Breiah - made a custom spinner so that the text won't clip
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
            campusSpinner.adapter = adapter

            // pre-selects leeian's school (which is upang urdaneta)
            campusSpinner.setSelection(7)
        }
        ArrayAdapter.createFromResource(
            this,
            R.array.course_array,
            R.layout.custom_spinner_selected_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
            courseSpinner.adapter = adapter

            // pre-selects leeian's course (which is bs computer science)
            courseSpinner.setSelection(3)
        }
        campusSpinner.isEnabled = false
        courseSpinner.isEnabled = false
        birthdateField.isEnabled = false


        editButton.setOnClickListener {
            isEditable = !isEditable

            nameField.isEnabled = isEditable
            emailField.isEnabled = isEditable
            phoneField.isEnabled = isEditable
            campusSpinner.isEnabled = isEditable
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
        campusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // If the selected item is not upang urdaneta, show a toast message
                if (position != 7) {
                    // Show a Toast message
                    Toast.makeText(
                        this@ProfileActivity,
                        "This campus is not available yet.",
                        Toast.LENGTH_SHORT
                    ).show()

                    // should go back to upang urdaneta
                    campusSpinner.setSelection(previousValidSelection)
                } else {
                    previousValidSelection = position
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
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

    // IM SO STUPID
    // THIS FUNCTION ENSURES THAT THE APPROPRIATE ICON IS CHECKED EVEN WHEN YOU PRESS THE BACK BUTTON
    // DO NOT COPY THIS FUNCTION TO NESTED ACTIVITIES BECAUSE IT WILL BREAK THE APP
    // THANKS
    // I WASTED 3 HOURS ON THIS
    override fun onResume() {
        super.onResume()
//        bottomNavigationView.selectedItemId = R.id.nav_profile
        unselectBottomNavIcon(bottomNavigationView)
    }

    fun initializeNavigationStuff() {
        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigationView = findViewById(R.id.navigation_view)
        topAppBar = findViewById(R.id.topAppBar)

        TopAppBarHelper.setupTopAppBar(this, topAppBar, drawerLayout, "Profile")
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        DrawerNavigationHelper.setupDrawerNavigation(this, drawerLayout, navigationView)

        unselectBottomNavIcon(bottomNavigationView)
    }

}
