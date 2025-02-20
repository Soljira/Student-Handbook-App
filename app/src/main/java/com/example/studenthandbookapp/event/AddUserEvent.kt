package com.example.studenthandbookapp.event

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.dataclasses.Event
import com.example.studenthandbookapp.helpers.BottomNavigationHelper
import com.example.studenthandbookapp.helpers.BottomNavigationHelper.unselectBottomNavIcon
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.helpers.TopAppBarHelper
import com.example.studenthandbookapp.helpers.FirestoreFunctions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Timestamp
import java.util.Date

class AddUserEvent : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var topAppBar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_user_event)
        initializeNavigationStuff()

        val btnClear = findViewById<Button>(R.id.clearBtn)

        btnClear.setOnClickListener {
            val titleEditText = findViewById<EditText>(R.id.title)
            val descriptionEditText = findViewById<EditText>(R.id.eventDesc)
            val dateEditText = findViewById<EditText>(R.id.addDate)
            val locationEditText = findViewById<EditText>(R.id.location)

            titleEditText.text.clear()
            descriptionEditText.text.clear()
            dateEditText.text.clear()
            locationEditText.text.clear()
        }

        // TODO: Bale gagawin mo, kukunin mo ng laman ng mga text sa lahat ng edittext
        // TODO: Gumawa na ako ng script para i-add na sa firestore ung entered data. palitan mo nalng ung ipopoint ko


        val event = Event(
            title = "Orayt",
            date = Timestamp(Date()),
            description = "Sample Description",
            location = "Sample Location"
        )
        // kung may textview ka na title ung id, para maset ung event title, gawin mo lang to
        // event.title = titleTextView.text.toString()

        FirestoreFunctions.saveEventToFirestore(this, "events_user", event)


    }






    override fun onResume() {
        super.onResume()
        unselectBottomNavIcon(bottomNavigationView)
    }

    fun initializeNavigationStuff() {
        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigationView = findViewById(R.id.navigation_view)
        topAppBar = findViewById(R.id.topAppBar)

        TopAppBarHelper.setupTopAppBar(this, topAppBar, drawerLayout, "Events")
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        DrawerNavigationHelper.setupDrawerNavigation(this, drawerLayout, navigationView)

        unselectBottomNavIcon(bottomNavigationView)
    }
}
