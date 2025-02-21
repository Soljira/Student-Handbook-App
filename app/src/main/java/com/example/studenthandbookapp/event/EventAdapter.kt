package com.example.studenthandbookapp.event

import android.content.Intent
import com.example.studenthandbookapp.dataclasses.Event
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.studenthandbookapp.R
import java.text.SimpleDateFormat
import java.util.Locale

class EventAdapter(
    private var eventList: List<Pair<String, Pair<String, Event>>>, // Now includes documentId, eventType, and Event
    private val onEventDetailsClick: (String, String) -> Unit // eventType, documentId
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val (documentId, typedEvent) = eventList[position]
        val (eventType, event) = typedEvent
        holder.bind(documentId, eventType, event)
    }

    override fun getItemCount() = eventList.size

    fun updateEvents(newEvents: List<Pair<String, Pair<String, Event>>>) {
        eventList = newEvents.sortedBy { it.second.second.date?.toDate() }
        notifyDataSetChanged()
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTextView: TextView = itemView.findViewById(R.id.eventDate)
        private val eventDetails: LinearLayout = itemView.findViewById(R.id.eventDetails)
        private val titleTextView: TextView = eventDetails.findViewById(R.id.eventTitle)
        private val typeTextView: TextView = eventDetails.findViewById(R.id.eventType)
        private val eventDesc: TextView = eventDetails.findViewById(R.id.eventDescription)

        fun bind(documentId: String, eventType: String, event: Event) {
            val formattedDate = event.date?.let { timestamp ->
                SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(timestamp.toDate())
            } ?: "No Date"

            dateTextView.text = formattedDate
            titleTextView.text = event.title
            eventDesc.text = event.description
            typeTextView.text = eventType

            eventDetails.setOnClickListener {
                onEventDetailsClick(eventType, documentId)
            }
        }
    }
}
