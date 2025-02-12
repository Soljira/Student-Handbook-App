package com.example.studenthandbookapp.map

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.studenthandbookapp.R

/**
 * A simple [Fragment] subclass.
 * Use the [FourthFloorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FourthFloorFragment : Fragment(R.layout.fragment_map_fourth_floor) {
    lateinit var dialog : MapCustomDialogFragment

    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        val closeButton: ImageButton = view.findViewById(R.id.btnBack)
        closeButton.setOnClickListener {
            dismissFragment()
        }

        val btnChemLab: Button = view.findViewById(R.id.btnChemLab)
        val btnPhysicsLab: Button = view.findViewById(R.id.btnPhysicsLab)
        val btnRoom401: Button = view.findViewById(R.id.btnRoom401)
        val btnRoom402: Button = view.findViewById(R.id.btnRoom402)
        val btnRoom403: Button = view.findViewById(R.id.btnRoom403)
        val btnRoom404: Button = view.findViewById(R.id.btnRoom404)
        val btnTertiaryFacultyRoom: Button = view.findViewById(R.id.btnTertiaryFacultyRoom)
        val btnSHSFacultyRoom: Button = view.findViewById(R.id.btnSHSFacultyRoom)

        btnChemLab.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Chemistry Lab",
                imageResId = R.drawable.map_chemistry_lab
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnPhysicsLab.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Physics Lab",
                imageResId = R.drawable.map_physics_lab
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnRoom401.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 401",
                imageResId = R.drawable.map_room_401
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnRoom402.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 402",
                imageResId = R.drawable.map_room_402
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnRoom403.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 403",
                imageResId = R.drawable.map_room_403
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnRoom404.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 404",
                imageResId = R.drawable.map_room_404
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnTertiaryFacultyRoom.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Tertiary Faculty Room",
                imageResId = R.drawable.map_tertiary_faculty_room
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnSHSFacultyRoom.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "SHS Faculty Room",
                imageResId = R.drawable.map_shs_faculty_room
            )
            dialog.show(childFragmentManager, "customDialog")
        }
    }

    private fun dismissFragment() {
        // Remove this fragment
        parentFragmentManager.beginTransaction().remove(this).commit()
    }
}