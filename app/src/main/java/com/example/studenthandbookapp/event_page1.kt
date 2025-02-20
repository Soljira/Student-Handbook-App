package com.example.studenthandbookapp

import Event
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class EventPage1 : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter  // Renamed to eventAdapter
    private lateinit var fullEventList: List<Event>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_page1)

        val spinner: Spinner = findViewById(R.id.spinner_filter)
        val filters = arrayOf("All Events", "Past Events", "Upcoming Events")

        val spinnerAdapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, filters) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
        }
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        recyclerView = findViewById(R.id.recycler_events)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fullEventList = listOf(
            Event("Hackathon", "Coding competition", "Feb", "10", LocalDate.of(2025, 2, 10)),
            Event("Game Jam", "48-hour game dev", "Feb", "15", LocalDate.of(2025, 2, 15)),
            Event("AI Summit", "AI conference", "Mar", "05", LocalDate.of(2025, 3, 5)),
            Event("Cybersecurity Talk", "Security event", "Mar", "22", LocalDate.of(2025, 3, 22)),
            Event("Data Science Meetup", "Networking event", "Apr", "12", LocalDate.of(2025, 4, 12))
        )

        // Set initial Adapter
        eventAdapter = EventAdapter(fullEventList)
        recyclerView.adapter = eventAdapter

        // Spinner Item Selection
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                filterEvents(filters[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterEvents(filter: String) {
        val today = LocalDate.now()
        val filteredList = when (filter) {
            "Past Events" -> fullEventList.filter { it.date.isBefore(today) }
            "Upcoming Events" -> fullEventList.filter { it.date.isAfter(today) }
            else -> fullEventList  // "All Events"
        }

        // Update RecyclerView
        eventAdapter = EventAdapter(filteredList)
        recyclerView.adapter = eventAdapter
    }
}
