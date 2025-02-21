package com.example.studenthandbookapp.calendar

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
import com.google.firebase.firestore.FirebaseFirestore

class EventFragment : Fragment() {

    private lateinit var txtEventHeader: TextView
    private lateinit var eventListContainer: LinearLayout
    private val db = FirebaseFirestore.getInstance()

    companion object {
        private const val ARG_DATE = "selected_date"

        fun newInstance(selectedDate: String): EventFragment {
            val fragment = EventFragment()
            val args = Bundle()
            args.putString(ARG_DATE, selectedDate)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_event, container, false)
        txtEventHeader = view.findViewById(R.id.txtEventDate)
        eventListContainer = view.findViewById(R.id.eventListContainer)

        val selectedDate = arguments?.getString(ARG_DATE)
        txtEventHeader.text = selectedDate ?: "No Date Selected"

        if (selectedDate != null) {
            fetchEventsForDate(selectedDate)
        }

        return view
    }

    private fun fetchEventsForDate(date: String) {
        db.collection("events")
            .whereEqualTo("date", date)
            .get()
            .addOnSuccessListener { documents ->
                eventListContainer.removeAllViews()
                if (documents.isEmpty) {
                    val noEventsTextView = TextView(requireContext()).apply {
                        text = "No events for this day"
                        textSize = 16f
                    }
                    eventListContainer.addView(noEventsTextView)
                } else {
                    for (document in documents) {
                        val event = document.toObject(Event::class.java)
                        addEventToUI(event)
                    }
                }
            }
            .addOnFailureListener { e ->
                println("Error fetching events: ${e.message}")
            }
    }

    private fun addEventToUI(event: Event) {
        val eventTextView = TextView(requireContext()).apply {
            text = "${event.title}: ${event.description}"
            textSize = 14f
        }
        eventListContainer.addView(eventTextView)
    }
}
