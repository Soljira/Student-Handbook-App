package com.example.studenthandbookapp.event

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.calendar.CalendarActivity
import com.example.studenthandbookapp.dataclasses.Event
import com.example.studenthandbookapp.helpers.FirestoreFunctions
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.Timestamp
import java.util.Calendar
import java.util.Date

class AddUserEventActivity : AppCompatActivity() {
    private lateinit var topAppBar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user_event)

        setupTopAppBar()

        val btnClear = findViewById<Button>(R.id.clearBtn)
        val btnSave = findViewById<Button>(R.id.saveBtn)

        val titleEditText = findViewById<EditText>(R.id.title)
        val descriptionEditText = findViewById<EditText>(R.id.eventDesc)
        val dateEditText = findViewById<EditText>(R.id.addDate)
        val locationEditText = findViewById<EditText>(R.id.location)

        dateEditText.setOnClickListener {
            showDatePicker(dateEditText)
        }

        btnClear.setOnClickListener {
            clearFields(titleEditText, descriptionEditText, dateEditText, locationEditText)
        }

        btnSave.setOnClickListener {
            saveEvent(titleEditText, descriptionEditText, dateEditText, locationEditText)
        }
    }

    private fun setupTopAppBar() {
        topAppBar = findViewById(R.id.topAppBar)
        topAppBar.title = "Add Event"
        topAppBar.setNavigationIcon(R.drawable.ic_back)
        topAppBar.setNavigationOnClickListener {
            finish() // Just close the activity when back is clicked
        }
        topAppBar.menu.clear() // Remove any menu items
    }

    private fun showDatePicker(dateEditText: EditText) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            dateEditText.setText(formattedDate)
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun clearFields(vararg editTexts: EditText) {
        editTexts.forEach { it.text.clear() }
    }

    private fun saveEvent(
        titleEditText: EditText,
        descriptionEditText: EditText,
        dateEditText: EditText,
        locationEditText: EditText
    ) {
        val title = titleEditText.text.toString().trim()
        val description = descriptionEditText.text.toString().trim()
        val dateString = dateEditText.text.toString().trim()
        val location = locationEditText.text.toString().trim()

        if (title.isEmpty() || description.isEmpty() || dateString.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val dateParts = dateString.split("-")
            val calendar = Calendar.getInstance().apply {
                set(dateParts[0].toInt(), dateParts[1].toInt() - 1, dateParts[2].toInt())
            }
            val eventDate = Timestamp(Date(calendar.timeInMillis))

            val event = Event(
                title = title,
                date = eventDate,
                description = description,
                location = location
            )

            FirestoreFunctions.saveEventToFirestore(this, "events_user", event)
            Toast.makeText(this, "Event saved successfully!", Toast.LENGTH_SHORT).show()
            clearFields(titleEditText, descriptionEditText, dateEditText, locationEditText)

            // Navigate back to Calendar with proper flags
            Intent(this, CalendarActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(this)
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Invalid date. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }
}