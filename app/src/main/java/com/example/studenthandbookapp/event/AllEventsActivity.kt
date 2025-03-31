package com.example.studenthandbookapp.event

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.dataclasses.Event
import com.example.studenthandbookapp.helpers.FirestoreFunctions
import com.google.android.material.appbar.MaterialToolbar

class AllEventsActivity : AppCompatActivity() {
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var spinner: Spinner
    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private lateinit var allEvents: MutableList<Pair<String, Pair<String, Event>>>
    private lateinit var eventDetailsLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events)

        setupTopAppBar()
        initializeEventDetailsLauncher()
        initializeRecyclerView()
        initializeSpinner()
    }

    override fun onResume() {
        super.onResume()
        fetchAndDisplayEvents()
    }

    private fun setupTopAppBar() {
        topAppBar = findViewById(R.id.topAppBar)
        topAppBar.title = "All Events"
        topAppBar.setNavigationIcon(R.drawable.ic_back)
        topAppBar.setNavigationOnClickListener {
            finish() // Close activity when back is pressed
        }
        topAppBar.menu.clear() // Remove any menu items
    }

    private fun initializeRecyclerView() {
        eventRecyclerView = findViewById(R.id.recycler_events)
        eventRecyclerView.layoutManager = LinearLayoutManager(this)

        eventAdapter = EventAdapter(emptyList()) { eventType, documentId ->
            Intent(this, EventDetailsActivity::class.java).apply {
                putExtra("EVENT_ID", documentId)
                putExtra("EVENT_TYPE", eventType)
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                eventDetailsLauncher.launch(this)
            }
        }

        eventRecyclerView.adapter = eventAdapter
        allEvents = mutableListOf()
    }

    private fun initializeSpinner() {
        spinner = findViewById(R.id.spinner_filter)
        val options = listOf("All", "School Event", "Holiday Event", "User Event")
        val adapter = ArrayAdapter(
            this,
            R.layout.custom_spinner_selected_item,
            options
        )
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                applyFilter(parent.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun fetchAndDisplayEvents(filterType: String = "All") {
        val eventTypes = listOf("events_holiday", "events_school", "events_user")
        val selectedTypes = if (filterType == "All") eventTypes else listOf(filterType)

        allEvents.clear()
        eventAdapter.updateEvents(emptyList())

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

    private fun applyFilter(selectedOption: String) {
        val rawEventType = reverseFormatEventType(selectedOption)
        val filteredEvents = if (rawEventType == "All") allEvents
        else allEvents.filter { it.second.first == rawEventType }
        eventAdapter.updateEvents(filteredEvents)
    }

    private fun initializeEventDetailsLauncher() {
        eventDetailsLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                fetchAndDisplayEvents()
            }
        }
    }

    private fun formatEventType(eventType: String): String {
        return when (eventType) {
            "events_holiday" -> "Holiday Event"
            "events_school" -> "School Event"
            "events_user" -> "User Event"
            else -> "Unknown Event"
        }
    }

    private fun reverseFormatEventType(formattedType: String): String {
        return when (formattedType) {
            "Holiday Event" -> "events_holiday"
            "School Event" -> "events_school"
            "User Event" -> "events_user"
            else -> "All"
        }
    }
}