package com.example.studenthandbookapp.manual

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.dataclasses.StudentManual
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.firestore.FirebaseFirestore

class Manual : AppCompatActivity() {
    private lateinit var btnNext: ImageButton
    private lateinit var btnPrev: ImageButton
    private var pageNum: Int = 1
    private var maxPages: Int = 27

    private lateinit var heading: TextView
    private lateinit var subheading: TextView
    private lateinit var pageNumber: TextView
    private lateinit var content: TextView

    private val db = FirebaseFirestore.getInstance()
    private var manualList: List<StudentManual> = emptyList()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual)

        setupTopAppBar()
        initializeViews()
        setupClickListeners()
        fetchManualData()
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
        heading = findViewById(R.id.heading)
        subheading = findViewById(R.id.subheading)
        pageNumber = findViewById(R.id.pageNumber)
        content = findViewById(R.id.content)

        btnNext = findViewById(R.id.btnNext)
        btnPrev = findViewById(R.id.btnPrev)
        btnPrev.visibility = ImageButton.INVISIBLE
    }

    private fun setupClickListeners() {
        btnPrev.setOnClickListener {
            updatePageNumber(0) // Previous page
        }

        btnNext.setOnClickListener {
            updatePageNumber(1) // Next page
        }
    }

    private fun fetchManualData() {
        db.collection("manual").get().addOnSuccessListener { documents ->
            manualList = documents.mapNotNull { it.toObject(StudentManual::class.java) }
                .sortedBy { it.pageNum ?: 0 }
            maxPages = manualList.size
            updatePageNumber(2) // Initialize with first page
        }
    }

    private fun updatePageNumber(direction: Int) {
        when (direction) {
            0 -> if (pageNum > 1) pageNum--
            1 -> if (pageNum < maxPages) pageNum++
        }

        // Update button visibility
        btnPrev.visibility = if (pageNum == 1) ImageButton.INVISIBLE else ImageButton.VISIBLE
        btnNext.visibility = if (pageNum == maxPages) ImageButton.INVISIBLE else ImageButton.VISIBLE

        // Update page content
        manualList.getOrNull(pageNum - 1)?.let { manual ->
            heading.text = manual.heading
            subheading.text = manual.subheading
            pageNumber.text = "Page ${manual.pageNum}"
            content.text = manual.content
        }
    }
}