package com.example.studenthandbookapp.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.util.Calendar
import java.util.Locale
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.event.AddUserEventActivity
import com.example.studenthandbookapp.event.EventListActivity
import com.google.android.material.appbar.MaterialToolbar

class CalendarActivity : AppCompatActivity() {

    private lateinit var btnToday: TextView
    private lateinit var btnEvents: ImageButton
    private lateinit var btnAddEvent: TextView
    private lateinit var btnPrev: ImageButton
    private lateinit var btnNext: ImageButton
    private lateinit var txtYear: TextView
    private lateinit var txtMonth: TextView
    private lateinit var eventHeader: TextView
    private lateinit var calendarGrid: GridView

    lateinit var eventDetailsLauncher: ActivityResultLauncher<Intent>

    private val calendar: Calendar = Calendar.getInstance()
    private var currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    private var selectedDay: Int = calendar.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        // Initialize the activity result launcher
        eventDetailsLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                onDaySelected(selectedDay) // Refresh events after changes
            }
        }

        initializeViews()
        setupTopAppBar()

        calendar.firstDayOfWeek = Calendar.SUNDAY
        btnToday.text = currentDay.toString()
        calendar.timeInMillis = System.currentTimeMillis()

        updateCalendar()
        onDaySelected(selectedDay)

        setupClickListeners()
    }

    private fun setupTopAppBar() {
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(topAppBar)

        // Enable back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        topAppBar.setNavigationOnClickListener {
            finish() // Handle back button press
        }
    }

    private fun initializeViews() {
        btnEvents = findViewById(R.id.btnEvents)
        btnToday = findViewById(R.id.btnToday)
        btnAddEvent = findViewById(R.id.btnAddEvent)
        btnPrev = findViewById(R.id.btnPrev)
        btnNext = findViewById(R.id.btnNext)
        txtYear = findViewById(R.id.txtYear)
        txtMonth = findViewById(R.id.txtMonth)
        eventHeader = findViewById(R.id.eventHeader)
        calendarGrid = findViewById(R.id.calendarGrid)
    }

    private fun setupClickListeners() {
        btnPrev.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateCalendar()
        }

        btnNext.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateCalendar()
        }

        btnEvents.setOnClickListener {
            startActivity(Intent(this, EventListActivity::class.java))
        }

        btnToday.setOnClickListener {
            calendar.timeInMillis = System.currentTimeMillis()
            selectedDay = currentDay
            updateCalendar()
            onDaySelected(currentDay)
        }

        btnAddEvent.setOnClickListener {
            startActivity(Intent(this, AddUserEventActivity::class.java))
        }

        calendarGrid.setOnItemClickListener { _, _, position, _ ->
            val days = generateCalendarDays()
            val selectedDate = days[position]
            if (selectedDate.isNotEmpty()) {
                onDaySelected(selectedDate.toInt())
            }
        }
    }

    private fun updateCalendar() {
        txtYear.text = calendar.get(Calendar.YEAR).toString()
        txtMonth.text = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

        val days = generateCalendarDays()

        calendarGrid.adapter = CalendarAdapter(
            this,
            days,
            selectedDay
        ) { day ->
            onDaySelected(day.toInt())
        }

        setGridViewHeight(calendarGrid, 7)
    }

    private fun generateCalendarDays(): MutableList<String> {
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val firstDayOfMonth = getFirstDayOfMonth(calendar)

        val days = mutableListOf<String>()
        repeat(firstDayOfMonth) { days.add("") }
        for (day in 1..daysInMonth) days.add(day.toString())

        return days
    }

    private fun onDaySelected(day: Int) {
        selectedDay = day
        val selectedDate = "${txtMonth.text} $day, ${txtYear.text}"
        eventHeader.text = selectedDate

        val fragment = EventFragment.newInstance(selectedDate)
        replaceFragment(fragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.eventFragmentContainer, fragment)
            .commit()
    }

    private fun getFirstDayOfMonth(calendar: Calendar): Int {
        val tempCal = calendar.clone() as Calendar
        tempCal.set(Calendar.DAY_OF_MONTH, 1)
        return tempCal.get(Calendar.DAY_OF_WEEK) - 1
    }

    private fun setGridViewHeight(gridView: GridView, numColumns: Int) {
        val listAdapter = gridView.adapter ?: return

        var totalHeight = 0
        val items = listAdapter.count
        val rows = (items + numColumns - 1) / numColumns

        for (i in 0 until rows) {
            val listItem = listAdapter.getView(i, null, gridView)
            listItem.measure(
                View.MeasureSpec.makeMeasureSpec(gridView.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            totalHeight += listItem.measuredHeight
        }

        val params = gridView.layoutParams
        params.height = totalHeight + (gridView.verticalSpacing * (rows - 1))
        gridView.layoutParams = params
        gridView.requestLayout()
    }
}