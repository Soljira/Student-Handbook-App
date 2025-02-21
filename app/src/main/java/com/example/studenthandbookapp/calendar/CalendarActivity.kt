package com.example.studenthandbookapp.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import java.util.Calendar
import java.util.Locale
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.event.AddUserEvent
import com.example.studenthandbookapp.event.EventList
import com.example.studenthandbookapp.helpers.BottomNavigationHelper
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.helpers.TopAppBarHelper
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class CalendarActivity : AppCompatActivity() {

//    private lateinit var btnMonth: Button
//    private lateinit var btnDay: Button
    private lateinit var btnToday: TextView
    private lateinit var btnEvents: ImageButton
    private lateinit var btnAddEvent: TextView
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

    lateinit var eventDetailsLauncher: ActivityResultLauncher<Intent>

    private val calendar: Calendar = Calendar.getInstance()
    var currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    var selectedDay: Int = calendar.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        // Initialize the activity result launcher
        eventDetailsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Refresh the current day view when returning with OK result (after deletion)
                onDaySelected(selectedDay)
            }
        }

        initializeViews()
        initializeNavigationStuff()

        calendar.firstDayOfWeek = Calendar.SUNDAY
        btnToday.text = currentDay.toString()


        // Set the calendar to today and update the UI
        calendar.timeInMillis = System.currentTimeMillis()

        updateCalendar()
        // Immediately select the current day on launch
        onDaySelected(selectedDay)

        btnPrev.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateCalendar()
        }

        btnNext.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateCalendar()
        }

        btnEvents.setOnClickListener {
            startActivity(Intent(this, EventList::class.java))
        }

        btnToday.setOnClickListener {
            calendar.timeInMillis = System.currentTimeMillis()
            selectedDay = currentDay
            updateCalendar()
            onDaySelected(currentDay)
        }

        btnAddEvent.setOnClickListener {
            startActivity(Intent(this, AddUserEvent::class.java))
        }


//        btnMonth.setOnClickListener {
//            Toast.makeText(this, "Month View", Toast.LENGTH_SHORT).show()
//            updateCalendar()
//        }

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
//        btnMonth = findViewById(R.id.btnMonth)
//        btnDay = findViewById(R.id.btnDay)
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

    private fun updateCalendar() {
        txtYear.text = calendar.get(Calendar.YEAR).toString()
        txtMonth.text = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

        val days = generateCalendarDays()

        // Get dates with events for the current month
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        calendarGrid.adapter = CalendarAdapter(
            this,
            days,
            selectedDay,
        ) { day ->
            onDaySelected(day.toInt())
        }

//        setGridViewHeight(calendarGrid, (days.size / 7) + 1)  //using this will mess up the calendar height for some reason
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
