package com.example.studenthandbookapp.event

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.dataclasses.Event
import com.example.studenthandbookapp.helpers.AddShitToFirestore
import com.example.studenthandbookapp.helpers.BottomNavigationHelper
import com.example.studenthandbookapp.helpers.BottomNavigationHelper.unselectBottomNavIcon
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.helpers.FirestoreFunctions
import com.example.studenthandbookapp.helpers.TopAppBarHelper
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


// nilagay ko na dito code ni jilbert from event_page1.kt
class EventList : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var topAppBar: MaterialToolbar

    lateinit var spinner: Spinner
    lateinit var eventRecyclerView: RecyclerView
    lateinit var eventAdapter: EventAdapter

    lateinit var allEvents: MutableList<Pair<String, Pair<String, Event>>> // documentId, (eventType, Event)
    lateinit var eventDetailsLauncher: ActivityResultLauncher<Intent>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_event_page1)
        FirebaseApp.initializeApp(this)

        initializeEventDetailsLauncher()

        initializeNavigationStuff()
        initializeSpinners()
        initializeRecyclerView()


        val dateTextView: TextView = findViewById(R.id.dateText)
        val currentDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
        dateTextView.text = currentDate


        val btnNewEvent = findViewById<Button>(R.id.btn_new_event)
        val btnShowAll = findViewById<Button>(R.id.btn_show_all)
//        val btnEventDetails = findViewById<Button>(R.id.eventDetails)



        btnNewEvent.setOnClickListener {
            startActivity(Intent(this, AddUserEvent::class.java))
        }
        btnShowAll.setOnClickListener {
            startActivity(Intent(this, AllEvents::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        unselectBottomNavIcon(bottomNavigationView)
        fetchAndDisplayEvents()
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

    fun initializeSpinners() {
        spinner = findViewById(R.id.spinner_filter)
        val options = listOf("Show: Upcoming", "Show: Past")
        val adapter = ArrayAdapter(
            this,
            R.layout.custom_spinner_selected_item,
            options
        )
        // ginamit ko ung ginawa mo dating custom spinners jilbert -bre
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedOption = parent.getItemAtPosition(position).toString()
                applyFilter(selectedOption)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented.")
            }
        }
    }

    fun initializeRecyclerView() {
        eventRecyclerView = findViewById(R.id.recycler_events)
        eventRecyclerView.layoutManager = LinearLayoutManager(this)

        eventAdapter = EventAdapter(emptyList()) { eventType, documentId ->
            val intent = Intent(this, EventDetails::class.java).apply {
                putExtra("EVENT_ID", documentId)
                putExtra("EVENT_TYPE", eventType)
            }
            startActivity(intent)
        }

        eventAdapter = EventAdapter(emptyList()) { eventType, documentId ->
            val intent = Intent(this, EventDetails::class.java).apply {
                putExtra("EVENT_ID", documentId)
                putExtra("EVENT_TYPE", eventType)
            }
            eventDetailsLauncher.launch(intent)
        }

        eventRecyclerView.adapter = eventAdapter
        allEvents = mutableListOf()
    }


    fun fetchAndDisplayEvents(filterType: String = "All") {
        val eventTypes = listOf("events_holiday", "events_school", "events_user")
        val selectedTypes = if (filterType == "All") eventTypes else listOf(filterType)

        allEvents.clear()
        eventAdapter.updateEvents(emptyList())

        var completedFetches = 0 // Counter to track completed fetches

        eventTypes.forEach { eventType ->
            FirestoreFunctions.getAllDocumentsWithIds(
                eventType,
                Event::class.java
            ) { documentsWithIds ->
                documentsWithIds?.let { documents ->
                    val formattedEvents = documents.map { (id, event) ->
                        id to (eventType to event)
                    }

                    allEvents.addAll(formattedEvents)
                }

                // Increment the completed fetch counter
                completedFetches++

                // Apply filter only when all fetches are completed
                if (completedFetches == eventTypes.size) {
                    applyFilter(spinner.selectedItem.toString())
                }
            }
        }
    }






    fun applyFilter(selectedOption: String) {
        val currentTime = Timestamp.now().toDate().time

        val filteredEvents = when (selectedOption) {
            "Show: Upcoming" -> allEvents.filter { (_, typedEvent) ->
                val (_, event) = typedEvent
                (event.date?.toDate()?.time ?: 0) >= currentTime
            }
            "Show: Past" -> allEvents.filter { (_, typedEvent) ->
                val (_, event) = typedEvent
                (event.date?.toDate()?.time ?: 0) < currentTime
            }
            else -> allEvents
        }

        eventAdapter.updateEvents(filteredEvents)
    }

    fun initializeEventDetailsLauncher() {
        eventDetailsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                fetchAndDisplayEvents()
            }
        }
    }


}
