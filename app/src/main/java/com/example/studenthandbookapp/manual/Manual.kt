package com.example.studenthandbookapp.manual

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.dataclasses.StudentManual
import com.example.studenthandbookapp.helpers.AddShitToFirestore.addStudentManualFromFile
import com.example.studenthandbookapp.helpers.BottomNavigationHelper
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.helpers.TopAppBarHelper
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore

class Manual : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var topAppBar: MaterialToolbar

    lateinit var btnNext: ImageButton
    lateinit var btnPrev: ImageButton
    var pageNum: Int = 1
    var maxPages: Int = 27

    lateinit var heading: TextView
    lateinit var subheading: TextView
    lateinit var pageNumber: TextView
    lateinit var content: TextView

    private val db = FirebaseFirestore.getInstance()
    private var manualList: List<StudentManual> = emptyList()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_manual)
        initializeNavigationStuff()

        heading = findViewById<TextView>(R.id.heading)
        subheading = findViewById<TextView>(R.id.subheading)
        pageNumber = findViewById<TextView>(R.id.pageNumber)
        content = findViewById<TextView>(R.id.content)

        // TODO: Open table of contents with fragments (dialogfragment we meet again)
        btnNext = findViewById<ImageButton>(R.id.btnNext)
        btnPrev = findViewById<ImageButton>(R.id.btnPrev)
        btnPrev.visibility = ImageButton.INVISIBLE

        btnPrev.setOnClickListener {
            updatePageNumber(0)
        }

        btnNext.setOnClickListener {
            updatePageNumber(1)
        }

        // do not uncomment this kasi maoverpopulate ung firestore
//        addStudentManualFromFile(this, "student_manual.txt", "manual")
        fetchManualData()

    }

    override fun onResume() {
        super.onResume()
        bottomNavigationView.selectedItemId = R.id.nav_manual
    }

    fun initializeNavigationStuff() {
        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigationView = findViewById(R.id.navigation_view)
        topAppBar = findViewById(R.id.topAppBar)

        TopAppBarHelper.setupTopAppBar(this, topAppBar, drawerLayout, "Student Manual")
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        DrawerNavigationHelper.setupDrawerNavigation(this, drawerLayout, navigationView)
    }

    private fun fetchManualData() {
        db.collection("manual").get().addOnSuccessListener { documents ->
            manualList = documents.mapNotNull { it.toObject(StudentManual::class.java) }
                .sortedBy { it.pageNum ?: 0 } // Safe fallback to 0 if null
            maxPages = manualList.size
            updatePageNumber(2) // Initialize with first page
        }
    }


    fun updatePageNumber(direction: Int) {
        when (direction) {
            0 -> if (pageNum > 1) pageNum--
            1 -> if (pageNum < maxPages) pageNum++
        }
        // MAWAWALA EITHER BUTTONS PAG NAREACH UNG MIN OR MAX PAGES
        btnPrev.visibility = if (pageNum == 1) ImageButton.INVISIBLE else ImageButton.VISIBLE
        btnNext.visibility = if (pageNum == maxPages) ImageButton.INVISIBLE else ImageButton.VISIBLE

        manualList.getOrNull(pageNum - 1)?.let { manual ->
            heading.text = manual.heading
            subheading.text = manual.subheading
            pageNumber.text = "Page ${manual.pageNum}"
            content.text = manual.content
        }
        // TODO: Maybe a toast for declaring page numbers and if user reached 0 or 100?

    }
}