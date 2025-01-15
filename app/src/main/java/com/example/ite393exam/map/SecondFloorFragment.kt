package com.example.ite393exam.map

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.ite393exam.R

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFloorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFloorFragment : Fragment(R.layout.fragment_map_second_floor) {
    lateinit var dialog : MapCustomDialogFragment

    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        val closeButton: ImageButton = view.findViewById(R.id.btnBack)
        closeButton.setOnClickListener {
            dismissFragment()
        }

        val btnRoom201: Button = view.findViewById(R.id.btnRoom201)
        val btnRoom202: Button = view.findViewById(R.id.btnRoom202)
        val btnRoom203: Button = view.findViewById(R.id.btnRoom203)
        val btnRoom204: Button = view.findViewById(R.id.btnRoom204)
        val btnRoom205: Button = view.findViewById(R.id.btn205)
        val btnRoom206: Button = view.findViewById(R.id.btnRoom206)
        val btnClinic: Button = view.findViewById(R.id.btnClinic)
        val btnRegistrar: Button = view.findViewById(R.id.btnRegistrar)
        val btnCSDL: Button = view.findViewById(R.id.btnCSDL)

        btnRoom201.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 201",
                imageResId = R.drawable.map_room_201
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnRoom202.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 202",
                imageResId = R.drawable.map_room_202
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnRoom203.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 203",
                imageResId = R.drawable.map_room_203
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnRoom204.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 204",
                imageResId = R.drawable.map_room_204
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnRoom205.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 205",
                imageResId = R.drawable.map_room_205
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnRoom206.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 206",
                imageResId = R.drawable.map_room_206
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnClinic.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Clinic",
                imageResId = R.drawable.map_clinic
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnRegistrar.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Registrar",
                imageResId = R.drawable.map_registrar
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnCSDL.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "CSDL",
                imageResId = R.drawable.map_csdl
            )
            dialog.show(childFragmentManager, "customDialog")
        }
    }

    private fun dismissFragment() {
        // Remove this fragment
        parentFragmentManager.beginTransaction().remove(this).commit()
    }
}