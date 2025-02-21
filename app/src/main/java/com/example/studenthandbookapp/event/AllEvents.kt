package com.example.studenthandbookapp.event

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.dataclasses.Event
import com.example.studenthandbookapp.helpers.BottomNavigationHelper
import com.example.studenthandbookapp.helpers.BottomNavigationHelper.unselectBottomNavIcon
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.helpers.FirestoreFunctions
import com.example.studenthandbookapp.helpers.TopAppBarHelper
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

// i just copied the code from EventList
class AllEvents : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var topAppBar: MaterialToolbar

    lateinit var spinner: Spinner
    lateinit var eventRecyclerView: RecyclerView
    lateinit var eventAdapter: EventAdapter

    lateinit var allEvents: MutableList<Pair<String, Pair<String, Event>>>
    lateinit var eventDetailsLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_events)
        initializeEventDetailsLauncher()

        initializeNavigationStuff()
        initializeRecyclerView()
        initializeSpinner()

        fetchAndDisplayEvents()

        //todo: maybe share the code between AllEvents and EventList? THEYRE SO SIMILAR
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

    fun initializeRecyclerView() {
        eventRecyclerView = findViewById(R.id.recycler_events)
        eventRecyclerView.layoutManager = LinearLayoutManager(this)

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

    fun initializeSpinner() {
        spinner = findViewById(R.id.spinner_filter)
        val options = listOf("All", "School Events", "Holidays", "User Events")
        val adapter = ArrayAdapter(
            this,
            R.layout.custom_spinner_selected_item,
            options
        )
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

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    fun fetchAndDisplayEvents() {
        val eventTypes = listOf("events_holiday", "events_school", "events_user")

        allEvents.clear()

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
                    applyFilter(spinner.selectedItem.toString())
                }
            }
        }
    }

    fun applyFilter(selectedOption: String) {
        val filteredEvents = when (selectedOption) {
            "School Events" -> allEvents.filter { it.second.first == "events_school" }
            "Holidays" -> allEvents.filter { it.second.first == "events_holiday" }
            "User Events" -> allEvents.filter { it.second.first == "events_user" }
            else -> allEvents // nonfiltered option
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
