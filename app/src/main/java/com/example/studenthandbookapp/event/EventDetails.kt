package com.example.studenthandbookapp.event

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
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

        // TODO: RECYCLER VIEW SHIT

        var title = findViewById<TextView>(R.id.title)

        // todo: firebase function for fetching eventid list
        val eventId = "e1oXYv4zFZXHGUTCZAj3" // school
//        val eventId = "5vHyDyenbUECUDdFyovW" // user
//        val eventId = "cisaBP2NtuToob8gDzDw"   // national
        val eventType = "events_school"
        // todo: create eventtest array based on the number of eventIDs
        var eventTest: Event? = Event()


        FirestoreFunctions.setEventObjectById(eventType, eventId) { event ->
//            eventTest = event  // eventTest is your empty Event object
//            title.text = eventTest?.title  // based sa findings ko above, its important na nandito mismo ung pagpalit ng title, kasi async nga
//            println("Title: ${eventTest?.title}")
            if (event != null) {
                eventTest = event
                title.text = event.title  // Update UI inside the async callback
                println("Title: ${event.title}")
            } else {
                println("Event not found or an error occurred.")
            }

            // For error checking only; uncomment if kailangan icheck
//            println("Title: ${eventTest?.title}")
//            println("Date: ${eventTest?.date}")
//            println("Description: ${eventTest?.description}")
//            println("Location: ${eventTest?.location}")
        }


        // todo convert timestamp (firestore) to string somehow




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