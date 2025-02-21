package com.example.studenthandbookapp.calendar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.dataclasses.Event
import com.example.studenthandbookapp.helpers.FirestoreFunctions
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EventFragment : Fragment() {

    private lateinit var eventListContainer: LinearLayout

    companion object {
        private const val ARG_DATE = "selected_date"

        fun newInstance(selectedDate: String): EventFragment {
            return EventFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DATE, selectedDate)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_event, container, false)
        eventListContainer = view.findViewById(R.id.eventListContainer)

        arguments?.getString(ARG_DATE)?.let { selectedDate ->
            fetchEventsForDate(selectedDate)
        }

        return view
    }

    private fun fetchEventsForDate(date: String) {
        val selectedDate = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).parse(date)
        if (selectedDate != null) {
            val startOfDay = Timestamp(selectedDate)
            val endOfDay = Timestamp(Date(selectedDate.time + 86400000 - 1))

            val collections = listOf("events_school", "events_holiday", "events_user")
            val allEvents = mutableListOf<Pair<Event, String>>()
            var loadedCollections = 0

            eventListContainer.removeAllViews()

            collections.forEach { collection ->
                FirestoreFunctions.getAllDocumentsFromCollection(collection, Event::class.java) { eventsList ->
                    // Avoid proceeding if the fragment is not attached
                    if (!isAdded) return@getAllDocumentsFromCollection

                    loadedCollections++

                    if (eventsList != null) {
                        val eventsForSelectedDate = eventsList.filter { event ->
                            event.date?.toDate()?.after(startOfDay.toDate()) == true &&
                                    event.date?.toDate()?.before(endOfDay.toDate()) == true
                        }
                        allEvents.addAll(eventsForSelectedDate.map { event -> event to collection })
                    } else {
                        println("Failed to retrieve events from $collection or collection doesn't exist.")
                    }

                    if (loadedCollections == collections.size) {
                        if (allEvents.isEmpty()) {
                            context?.let { safeContext ->
                                val noEventsTextView = TextView(safeContext).apply {
                                    text = "No events for this day"
                                    textSize = 16f
                                    setTextColor(resources.getColor(R.color.black, null))
                                }
                                eventListContainer.addView(noEventsTextView)
                            }
                        } else {
                            allEvents.forEach { (event, eventType) ->
                                addEventToUI(event, eventType)
                            }
                        }
                    }
                }
            }
        } else {
            println("Invalid date format: $date")
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun addEventToUI(event: Event, eventType: String) {
        context?.let { safeContext ->
            val eventView = LayoutInflater.from(safeContext)
                .inflate(R.layout.event_item, eventListContainer, false)

            val eventTitleTv = eventView.findViewById<TextView>(R.id.eventTitleTv)
            val eventTypeTv = eventView.findViewById<TextView>(R.id.eventTypeTv)
            val eventDescriptionTv = eventView.findViewById<TextView>(R.id.eventDescriptionTv)

            eventTitleTv.text = event.title
            eventDescriptionTv.text = event.description

            eventTypeTv.text = when (eventType) {
                "events_school" -> "School Event"
                "events_holiday" -> "Holiday Event"
                "events_user" -> "User Event"
                else -> "Unknown Event Type"
            }

            eventListContainer.addView(eventView)
        }
    }
}
