package com.example.studenthandbookapp.event

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.calendar.CalendarActivity
import com.example.studenthandbookapp.dataclasses.Event
import com.example.studenthandbookapp.helpers.BottomNavigationHelper
import com.example.studenthandbookapp.helpers.BottomNavigationHelper.unselectBottomNavIcon
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.helpers.TopAppBarHelper
import com.example.studenthandbookapp.helpers.FirestoreFunctions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Timestamp
import java.util.Calendar
import java.util.Date

class AddUserEvent : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var topAppBar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_user_event)
        initializeNavigationStuff()

        val btnClear = findViewById<Button>(R.id.clearBtn)
        val btnSave = findViewById<Button>(R.id.saveBtn)

        val titleEditText = findViewById<EditText>(R.id.title)
        val descriptionEditText = findViewById<EditText>(R.id.eventDesc)
        val dateEditText = findViewById<EditText>(R.id.addDate)
        val locationEditText = findViewById<EditText>(R.id.location)

        dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                dateEditText.setText(formattedDate)
            }, year, month, day)

            datePickerDialog.show()
        }

        btnClear.setOnClickListener {
            titleEditText.text.clear()
            descriptionEditText.text.clear()
            dateEditText.text.clear()
            locationEditText.text.clear()
        }

        btnSave.setOnClickListener {
            val title = titleEditText.text.toString().trim()
            val description = descriptionEditText.text.toString().trim()
            val dateString = dateEditText.text.toString().trim()
            val location = locationEditText.text.toString().trim()

            if (title.isEmpty() || description.isEmpty() || dateString.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dateParts = dateString.split("-")
            val calendar = Calendar.getInstance()
            try {
                calendar.set(dateParts[0].toInt(), dateParts[1].toInt() - 1, dateParts[2].toInt())
                val eventDate = Timestamp(Date(calendar.timeInMillis))

                val event = Event(
                    title = title,
                    date = eventDate,
                    description = description,
                    location = location
                )

                // Call Firestore function with callback
                FirestoreFunctions.saveEventToFirestore(this, "events_user", event)
                runOnUiThread {
                    Toast.makeText(this, "Event saved successfully!", Toast.LENGTH_SHORT).show()

                    titleEditText.text.clear()
                    descriptionEditText.text.clear()
                    dateEditText.text.clear()
                    locationEditText.text.clear()

                    // Notify the Calendar and refresh it
                    val intent = Intent(this, CalendarActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }

            } catch (e: Exception) {
                Toast.makeText(this, "Invalid date. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        unselectBottomNavIcon(bottomNavigationView)
    }

    fun initializeNavigationStuff() {
        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigationView = findViewById(R.id.navigation_view)
        topAppBar = findViewById(R.id.topAppBar)

        TopAppBarHelper.setupTopAppBar(this, topAppBar, drawerLayout, "Events")
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        DrawerNavigationHelper.setupDrawerNavigation(this, drawerLayout, navigationView)

        unselectBottomNavIcon(bottomNavigationView)
    }
}
