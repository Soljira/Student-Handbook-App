package com.example.ite393exam.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.example.ite393exam.R

/**
 * A simple [Fragment] subclass.
 * Use the [ThirdFloorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdFloorFragment : Fragment(R.layout.fragment_map_third_floor) {
    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        val closeButton: ImageButton = view.findViewById(R.id.btnBack)
        closeButton.setOnClickListener {
            dismissFragment()
        }
    }

    private fun dismissFragment() {
        // Remove this fragment
        parentFragmentManager.beginTransaction().remove(this).commit()
    }
}