package com.example.ite393exam.modalities

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.ite393exam.R

/**
 * A simple [Fragment] subclass.
 * Use the [FlexRemoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FlexRemoteFragment : Fragment(R.layout.fragment_flex_remote) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnNext = view.findViewById<ImageButton>(R.id.btn_next)

        btnNext.setOnClickListener {
            // Navigate to RemoteCourses Activity
            val intent = Intent(requireContext(), RemoteCoursesActivity::class.java)
            startActivity(intent)
        }

    }

}