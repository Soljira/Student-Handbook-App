package com.example.studenthandbookapp.event

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.dataclasses.Event
import com.example.studenthandbookapp.helpers.BottomNavigationHelper
import com.example.studenthandbookapp.helpers.BottomNavigationHelper.unselectBottomNavIcon
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.helpers.TopAppBarHelper
import com.example.studenthandbookapp.helpers.FirestoreFunctions
import com.example.studenthandbookapp.helpers.AddShitToFirestore

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class EventDetails : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var topAppBar: MaterialToolbar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_event_details)
        initializeNavigationStuff()

        //todo delete functionality for user-added events

        // hnd na to need vv
//        AddEventsToFirestore.addEventsFromFile(this, "holidays_events.txt")


//        val eventId = "e1oXYv4zFZXHGUTCZAj3" // school
//        val eventId = "5vHyDyenbUECUDdFyovW" // user
//        val eventId = "cisaBP2NtuToob8gDzDw"   // national
//        val eventType = "events_school"
        var eventTest: Event? = Event()


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