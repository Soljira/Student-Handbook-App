package com.example.studenthandbookapp.event

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.dataclasses.Event
import com.example.studenthandbookapp.helpers.FirestoreFunctions

import com.google.android.material.appbar.MaterialToolbar

class EventDetailsActivity : AppCompatActivity() {
    lateinit var topAppBar: MaterialToolbar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        setupTopAppBar()


        //todo delete functionality for user-added events

//        val eventId = "e1oXYv4zFZXHGUTCZAj3" // school
//        val eventId = "5vHyDyenbUECUDdFyovW" // user
//        val eventId = "cisaBP2NtuToob8gDzDw"   // national
//        val eventType = "events_school"
        var eventTest: Event? = Event()
        val btnDelete = findViewById<Button>(R.id.btnDelete)


        // ALGORITHM:
        //  1. get document id and eventType from EventList (via putExtra)
        //  2. Use getEventById (from FirestoreFunctions) and change the text in xml according to the document

        // Get the data from the prev intent (ung EventList)
        val eventId = intent.getStringExtra("EVENT_ID") ?: ""
        val eventType = intent.getStringExtra("EVENT_TYPE") ?: ""
        if (eventId.isEmpty() || eventType.isEmpty()) {
            Toast.makeText(this, "Error: Event details not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val eventTitleTv = findViewById<TextView>(R.id.title)
        val eventDateTimeTv = findViewById<TextView>(R.id.dateTime)
        val eventLocationTv = findViewById<TextView>(R.id.location)
        val eventDescTv = findViewById<TextView>(R.id.description)


        // Use the getEventById function to fetch the event FROM FIRESTORSE
        FirestoreFunctions.getEventById(eventType, eventId) { event ->
            if (event != null) {
                eventTitleTv.text = event.title

                // UPDATE UI WITH THE FETCHED DATA
                val formattedDate = FirestoreFunctions.formatTimestampToDateString(event.date)
                eventDateTimeTv.text = formattedDate
                eventDescTv.text = event.description
                eventLocationTv.text = event.location
            } else {
                Toast.makeText(this, "Error: Event not found", Toast.LENGTH_SHORT).show()
                finish()
            }
        }


        // NOTE: set eventType to events_user (only; the other events should be left alone) just in case
        // TODO: THIS SHOULD ONLY SHOW UP WHEN EVENTYPE IS EVENTS_USER

        if (eventType != "events_user") {
            btnDelete.visibility = Button.GONE
        } else {
            btnDelete.visibility = Button.VISIBLE
            btnDelete.setOnClickListener {
                FirestoreFunctions.deleteEvent("events_user", eventId) { success ->
                    if (success) {
                        Toast.makeText(this, "Event deleted", Toast.LENGTH_SHORT).show()
                        setResult(RESULT_OK) // Indicate success
                        finish()
                    } else {
                        Toast.makeText(this, "Error: Event not deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
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
}