package com.example.ite393exam

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ite393exam.map.MapActivity

class SchoolSelection : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_selection)

        val cardUpang = findViewById<androidx.cardview.widget.CardView>(R.id.cardUpang)
        val cardAU = findViewById<androidx.cardview.widget.CardView>(R.id.cardAU)
        val cardCOC = findViewById<androidx.cardview.widget.CardView>(R.id.cardCOC)
        val cardUI = findViewById<androidx.cardview.widget.CardView>(R.id.cardUI)
        val cardSWU = findViewById<androidx.cardview.widget.CardView>(R.id.cardSWU)
        val cardSJC = findViewById<androidx.cardview.widget.CardView>(R.id.cardSJC)
        val cardRC = findViewById<androidx.cardview.widget.CardView>(R.id.cardRC)
        val cardRCL = findViewById<androidx.cardview.widget.CardView>(R.id.cardRCL)
        val cardUCL = findViewById<androidx.cardview.widget.CardView>(R.id.cardUCL)
        val cardHorizon = findViewById<androidx.cardview.widget.CardView>(R.id.cardHorizon)

        cardUpang.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
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
}