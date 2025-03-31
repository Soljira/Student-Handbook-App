package com.example.studenthandbookapp.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.dataclasses.Event
import com.example.studenthandbookapp.event.EventAdapter
import com.example.studenthandbookapp.event.EventDetailsActivity
import com.example.studenthandbookapp.event.EventListActivity
import com.example.studenthandbookapp.helpers.BottomNavigationHelper
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.helpers.FirestoreFunctions
import com.example.studenthandbookapp.helpers.TopAppBarHelper
import com.example.studenthandbookapp.ui.theme.JosefinSans
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Timestamp

class Home : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var topAppBar: MaterialToolbar

    lateinit var eventRecyclerView: RecyclerView
    lateinit var eventAdapter: EventAdapter
    lateinit var allEvents: MutableList<Pair<String, Pair<String, Event>>> // documentId, (eventType, Event)

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
//        setContent {
//            CenterAlignedTopAppBarExample()
//        }

        val btnEvent = findViewById<ImageButton>(R.id.btnEvent)
        btnEvent.setOnClickListener {
            startActivity(Intent(this, EventListActivity::class.java))
        }
        initializeNavigationStuff()

        initializeRecyclerView()

        fetchAndDisplayUpcomingEvents()

    }


    // Handle back press for drawer
    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        bottomNavigationView.selectedItemId = R.id.nav_home
    }

    // LAHAT NG RELATED TO NAVIGATION NANDITO OKAY????
    fun initializeNavigationStuff() {
        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigationView = findViewById(R.id.navigation_view)
        topAppBar = findViewById(R.id.topAppBar)

        TopAppBarHelper.setupTopAppBar(this, topAppBar, drawerLayout, "Home")
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        DrawerNavigationHelper.setupDrawerNavigation(this, drawerLayout, navigationView)

        bottomNavigationView.selectedItemId = R.id.nav_home
    }


    fun initializeRecyclerView() {
        eventRecyclerView = findViewById(R.id.recycler_upcoming_events)
        eventRecyclerView.layoutManager = LinearLayoutManager(this)

        eventAdapter = EventAdapter(emptyList()) { eventType, documentId ->
            val intent = Intent(this, EventDetailsActivity::class.java).apply {
                putExtra("EVENT_ID", documentId)
                putExtra("EVENT_TYPE", eventType)
            }
            startActivity(intent)
        }

        eventRecyclerView.adapter = eventAdapter
        allEvents = mutableListOf()
    }

    fun fetchAndDisplayUpcomingEvents() {
        val eventTypes = listOf("events_holiday", "events_school", "events_user")
        val currentTime = Timestamp.now().toDate().time

        eventTypes.forEach { eventType ->
            FirestoreFunctions.getAllDocumentsWithIds(
                eventType,
                Event::class.java
            ) { documentsWithIds ->
                documentsWithIds?.let { documents ->
                    val formattedEvents = documents.map { (id, event) ->
                        id to (eventType to event)
                    }

                    allEvents.addAll(formattedEvents)
                }

                // Filter events based on upcoming date
                val filteredEvents = allEvents.filter { (_, typedEvent) ->
                    val (_, event) = typedEvent
                    (event.date?.toDate()?.time ?: 0) >= currentTime
                }

                eventAdapter.updateEvents(filteredEvents)
            }
        }
    }






    /**
     * FROM: https://developer.android.com/develop/ui/compose/components/app-bars
     */
    // I FUCKED UP DI KO PA ALAM
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @Preview
    fun CenterAlignedTopAppBarExample() {

        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            "Home",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = JosefinSans,
                            fontWeight = FontWeight.Normal
                        )
                    },

                    // MENU ICON
                    navigationIcon = {
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    },

                    // PROFILE ICON; WILL LEAD TO PROFILE PAGE
                    actions = {
                        IconButton(onClick = { /* do something */ }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_person),
                                contentDescription = "Profile",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            }
        ) {
            //scroll content goes here!!!!
        }
    }

    @Composable
    fun ScrollContent(innerPadding: PaddingValues) {
        // DI KO PA ALAM ANO ILALAGAY
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            repeat(20) { // Dummy content
                Text("Item $it", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(8.dp))
            }
        }
    }
}
