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

        view.setOnClickListener {
            if (day.isNotEmpty()) onDayClick(day)
        }

        return view
    }
}
