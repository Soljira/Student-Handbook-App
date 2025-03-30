package com.example.studenthandbookapp.landingpage

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.studenthandbookapp.R

class SchoolSelection : AppCompatActivity() {
    lateinit var flFragmentContainer: FrameLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_school_selection)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cardUpang = findViewById<CardView>(R.id.cardUpang)
        val cardAU = findViewById<CardView>(R.id.cardAU)
        val cardCOC = findViewById<CardView>(R.id.cardCOC)
        val cardUI = findViewById<CardView>(R.id.cardUI)
        val cardSWU = findViewById<CardView>(R.id.cardSWU)
        val cardSJC = findViewById<CardView>(R.id.cardSJC)
        val cardRC = findViewById<CardView>(R.id.cardRC)
        val cardRCL = findViewById<CardView>(R.id.cardRCL)
        val cardUCL = findViewById<CardView>(R.id.cardUCL)
        val cardHorizon = findViewById<CardView>(R.id.cardHorizon)

        flFragmentContainer = findViewById(R.id.flFragmentContainer)

        cardUpang.setOnClickListener {
//            startActivity(Intent(this, EditProfile::class.java))
            fragmentBeginTransaction(SelectUpangCampusFragment())
//            finish()
        }

        // THESE SCHOOLS ARE BEYOND OUR SCOPE
        cardAU.setOnClickListener {
            showToast()
        }

        cardCOC.setOnClickListener {
            showToast()
        }

        cardUI.setOnClickListener {
            showToast()
        }

        cardSWU.setOnClickListener {
            showToast()
        }

        cardSJC.setOnClickListener {
            showToast()
        }

        cardRC.setOnClickListener {
            showToast()
        }

        cardRCL.setOnClickListener {
            showToast()
        }

        cardUCL.setOnClickListener {
            showToast()
        }

        cardHorizon.setOnClickListener {
            showToast()
        }

    }

    fun showToast() {
        Toast.makeText(
            this,
            "Not yet available!",
            Toast.LENGTH_SHORT)
            .show()
    }

    // Wrote this function to make my code more readable
    fun fragmentBeginTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragmentContainer, fragment)
            addToBackStack(null)
            commit()  // to actually apply changes
        }
    }
}