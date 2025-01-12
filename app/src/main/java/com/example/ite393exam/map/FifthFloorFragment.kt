package com.example.ite393exam.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.example.ite393exam.R

/**
 * A simple [Fragment] subclass.
 * Use the [FifthFloorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FifthFloorFragment : Fragment(R.layout.fragment_map_fifth_floor) {
    lateinit var dialog : MapCustomDialogFragment

    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        val closeButton: ImageButton = view.findViewById(R.id.btnBack)
        closeButton.setOnClickListener {
            dismissFragment()
        }

        val btnRoom505: Button = view.findViewById(R.id.btnRoom505)
        val btnRoom506: Button = view.findViewById(R.id.btnRoom506)
        val btnKitchenLab: Button = view.findViewById(R.id.btnKitchenLab)
        val btnDrawingLab: Button = view.findViewById(R.id.btnDrawingLab)
        val btnStudioLab: Button = view.findViewById(R.id.btnStudioLab)
        val btnCriminologyLab: Button = view.findViewById(R.id.btnCriminologyLab)
        val btnLibrary: Button = view.findViewById(R.id.btnLibrary)

        btnRoom505.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 505",
                imageResId = R.drawable.map_room_505
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnRoom506.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 506",
                imageResId = R.drawable.map_room_506
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnKitchenLab.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Kitchen Lab",
                imageResId = R.drawable.map_kitchen_lab
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnDrawingLab.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Drawing Lab",
                imageResId = R.drawable.map_drawing_lab
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnStudioLab.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Studio Lab",
                imageResId = R.drawable.map_studio_lab
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnCriminologyLab.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Criminology Lab",
                imageResId = R.drawable.map_criminology_lab
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnLibrary.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Library",
                imageResId = R.drawable.map_library
            )
            dialog.show(childFragmentManager, "customDialog")
        }
    }

    private fun dismissFragment() {
        // Remove this fragment
        parentFragmentManager.beginTransaction().remove(this).commit()
    }
}