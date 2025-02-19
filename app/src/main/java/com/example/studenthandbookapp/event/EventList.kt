package com.example.studenthandbookapp.event

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.studenthandbookapp.R

class EventList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.event_list)

        val spinner: Spinner = findViewById(R.id.spinner)
        val options = listOf("Show: Upcoming", "Show: Past")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)
        spinner.adapter = adapter
        spinner.setSelection(0)
    }
}
