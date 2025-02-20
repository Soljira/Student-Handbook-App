package com.example.studenthandbookapp
import Event
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(private val eventList: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.eventTitle)
        val description: TextView = view.findViewById(R.id.eventDescription)
        val date: TextView = view.findViewById(R.id.eventDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]

        // Set event details dynamically
        holder.title.text = event.title
        holder.description.text = event.description

        // Set date in the format "Month\nDay"
        holder.date.text = "${event.month}\n${event.day}"
    }

    override fun getItemCount() = eventList.size
}
