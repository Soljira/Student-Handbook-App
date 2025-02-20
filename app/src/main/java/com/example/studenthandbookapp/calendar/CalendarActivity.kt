package com.example.studenthandbookapp.calendar

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.Locale
import com.example.studenthandbookapp.R

class CalendarActivity : AppCompatActivity() {

    private lateinit var btnMonth: Button
    private lateinit var btnDay: Button
    private lateinit var btnToday: ImageButton
    private lateinit var btnPrev: ImageButton
    private lateinit var btnNext: ImageButton
    private lateinit var txtYear: TextView
    private lateinit var txtMonth: TextView
    private lateinit var calendarGrid: GridView

    private val calendar: Calendar = Calendar.getInstance()

    private var selectedDay: Int = calendar.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        btnMonth = findViewById(R.id.btnMonth)
        btnDay = findViewById(R.id.btnDay)
        btnToday = findViewById(R.id.btnToday)
        btnPrev = findViewById(R.id.btnPrev)
        btnNext = findViewById(R.id.btnNext)
        txtYear = findViewById(R.id.txtYear)
        txtMonth = findViewById(R.id.txtMonth)
        calendarGrid = findViewById(R.id.calendarGrid)

        updateCalendar()

        btnPrev.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateCalendar()
        }

        btnNext.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateCalendar()
        }

        btnToday.setOnClickListener {
            calendar.timeInMillis = System.currentTimeMillis()
            selectedDay = calendar.get(Calendar.DAY_OF_MONTH)
            updateCalendar()
        }

        btnMonth.setOnClickListener {
            Toast.makeText(this, "Month View", Toast.LENGTH_SHORT).show()
            updateCalendar()
        }
    }




    private fun updateCalendar() {
        txtYear.text = calendar.get(Calendar.YEAR).toString()
        txtMonth.text = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val firstDayOfMonth = getFirstDayOfMonth(calendar)

        val days = mutableListOf<String>()
        for (i in 1..firstDayOfMonth) days.add("")
        for (day in 1..daysInMonth) days.add(day.toString())

        calendarGrid.adapter = CalendarAdapter(this, days) { day ->
            selectedDay = day.toInt()
        }

        setGridViewHeight(calendarGrid, days.size / 7 + 1)
    }




    private fun getFirstDayOfMonth(calendar: Calendar): Int {
        val tempCal = calendar.clone() as Calendar
        tempCal.set(Calendar.DAY_OF_MONTH, 1)
        return (tempCal.get(Calendar.DAY_OF_WEEK) + 5) % 7
    }

    private fun setGridViewHeight(gridView: GridView, rows: Int) {
        val itemHeight = 120
        val totalHeight = rows * itemHeight
        gridView.layoutParams.height = totalHeight
        gridView.requestLayout()
    }
}



