package com.example.studenthandbookapp.calendar

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import java.util.Calendar
import java.util.Locale
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.helpers.BottomNavigationHelper
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.helpers.TopAppBarHelper
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class CalendarActivity : AppCompatActivity() {

    private lateinit var btnMonth: Button
    private lateinit var btnDay: Button
    private lateinit var btnToday: ImageButton
    private lateinit var btnPrev: ImageButton
    private lateinit var btnNext: ImageButton
    private lateinit var txtYear: TextView
    private lateinit var txtMonth: TextView
    private lateinit var eventHeader: TextView
    private lateinit var calendarGrid: GridView

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var topAppBar: MaterialToolbar

    private val calendar: Calendar = Calendar.getInstance()
    private var selectedDay: Int = calendar.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        initializeViews()
        initializeNavigationStuff()
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

        calendarGrid.setOnItemClickListener { _, _, position, _ ->
            val days = generateCalendarDays()
            val selectedDate = days[position]
            if (selectedDate.isNotEmpty()) {
                onDaySelected(selectedDate.toInt())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        bottomNavigationView.selectedItemId = R.id.nav_calendar
    }

    private fun initializeViews() {
        btnMonth = findViewById(R.id.btnMonth)
        btnDay = findViewById(R.id.btnDay)
        btnToday = findViewById(R.id.btnToday)
        btnPrev = findViewById(R.id.btnPrev)
        btnNext = findViewById(R.id.btnNext)
        txtYear = findViewById(R.id.txtYear)
        txtMonth = findViewById(R.id.txtMonth)
        eventHeader = findViewById(R.id.eventHeader)
        calendarGrid = findViewById(R.id.calendarGrid)
    }

    private fun updateCalendar() {
        txtYear.text = calendar.get(Calendar.YEAR).toString()
        txtMonth.text = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

        val days = generateCalendarDays()

        calendarGrid.adapter = CalendarAdapter(this, days) { day ->
            onDaySelected(day.toInt())
        }

        setGridViewHeight(calendarGrid, (days.size / 7) + 1)
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
        return (tempCal.get(Calendar.DAY_OF_WEEK) + 5) % 7
    }

    private fun setGridViewHeight(gridView: GridView, rows: Int) {
        val itemHeight = 120
        gridView.layoutParams.height = rows * itemHeight
        gridView.requestLayout()
    }

    private fun initializeNavigationStuff() {
        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigationView = findViewById(R.id.navigation_view)
        topAppBar = findViewById(R.id.topAppBar)

        TopAppBarHelper.setupTopAppBar(this, topAppBar, drawerLayout, "Calendar")
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        DrawerNavigationHelper.setupDrawerNavigation(this, drawerLayout, navigationView)

        bottomNavigationView.selectedItemId = R.id.nav_calendar
    }
}
