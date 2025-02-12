package com.example.studenthandbookapp.modalities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.studenthandbookapp.R

/**
 * A simple [Fragment] subclass.
 * Use the [Flex24Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Flex24Fragment : Fragment(R.layout.fragment_flex24) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnNext = view.findViewById<ImageButton>(R.id.btn_next)

        btnNext.setOnClickListener {
            // Navigate to UrdanetaCampus Activity
            val intent = Intent(requireContext(), UrdanetaCampus::class.java)
            startActivity(intent)
        }

    }

}