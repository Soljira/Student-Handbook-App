package com.example.studenthandbookapp.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.studenthandbookapp.R

class CalendarAdapter(
    private val context: Context,
    private val days: List<String>,
    private var selectedDay: Int, // Changed to var
    private val onDayClick: (String) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = days.size

    override fun getItem(position: Int): Any = days[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_calendar_day, parent, false)

        val txtDay = view.findViewById<TextView>(R.id.txtDay)
        val day = days[position]

        txtDay.text = day
        txtDay.alpha = if (day.isEmpty()) 0.3f else 1.0f

        if (day.isNotEmpty() && day.toIntOrNull() == selectedDay) {
            txtDay.setTextColor(context.getColor(R.color.white))
            txtDay.setBackgroundResource(R.drawable.selected_day_background)
        } else {
            txtDay.setTextColor(context.getColor(android.R.color.black))
            txtDay.setBackgroundResource(R.drawable.calendar_day_background)
        }

        view.setOnClickListener {
            if (day.isNotEmpty()) {
                selectedDay = day.toInt() // Update selected day
                notifyDataSetChanged() // Refresh the view
                onDayClick(day)
            }
        }

        return view
    }
}
