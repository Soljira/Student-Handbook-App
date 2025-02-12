package com.example.studenthandbookapp.scholarships

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.studenthandbookapp.R

class SchDescriptionsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sch_page2_descriptions)


        val backButton: ImageButton = findViewById(R.id.btn_back)
        val scholarshipTitle: TextView = findViewById(R.id.tv_sch_desc_title)
        val scholarshipDescription: TextView = findViewById(R.id.tv_sch_descriptions)

        val bundle: Bundle? = intent.extras
        val heading = bundle!!.getString("heading")
        val description = bundle.getString("description")

        scholarshipTitle.text = heading
        scholarshipDescription.text = description

        backButton.setOnClickListener {
            finish()
        }


    }
}