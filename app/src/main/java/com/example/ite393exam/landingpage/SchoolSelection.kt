package com.example.ite393exam.landingpage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.ite393exam.ProfileActivity
import com.example.ite393exam.R

class SchoolSelection : AppCompatActivity() {
    lateinit var flFragmentContainer: FrameLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_selection)

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
//            startActivity(Intent(this, ProfileActivity::class.java))
            fragmentBeginTransaction(SelectUpangCampusFragment())
//            finish()
        }

        // THESE SCHOOLS ARE BEYOND OUR SCOPE
        cardAU.setOnClickListener {
            showUnavailableToast()
        }

        cardCOC.setOnClickListener {
            showUnavailableToast()
        }

        cardUI.setOnClickListener {
            showUnavailableToast()
        }

        cardSWU.setOnClickListener {
            showUnavailableToast()
        }

        cardSJC.setOnClickListener {
            showUnavailableToast()
        }

        cardRC.setOnClickListener {
            showUnavailableToast()
        }

        cardRCL.setOnClickListener {
            showUnavailableToast()
        }

        cardUCL.setOnClickListener {
            showUnavailableToast()
        }

        cardHorizon.setOnClickListener {
            showUnavailableToast()
        }

    }

    fun showUnavailableToast() {
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