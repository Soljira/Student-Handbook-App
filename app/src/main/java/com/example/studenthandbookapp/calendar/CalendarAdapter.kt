package com.example.studenthandbookapp.calendar
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.BaseAdapter

class CalendarAdapter(
    private val context: Context,
    private val days: List<String>,
    private val onDayClick: (String) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = days.size

    override fun getItem(position: Int): Any = days[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)

        val dayText = view.findViewById<TextView>(android.R.id.text1)
        dayText.text = days[position]

        dayText.setOnClickListener {
            onDayClick(days[position])
        }

        return view
    }
}
