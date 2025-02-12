package com.example.studenthandbookapp.map

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.studenthandbookapp.R

/**
 * A simple [Fragment] subclass.
 * Use the [ThirdFloorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdFloorFragment : Fragment(R.layout.fragment_map_third_floor) {
    lateinit var dialog : MapCustomDialogFragment

    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        val closeButton: ImageButton = view.findViewById(R.id.btnBack)
        closeButton.setOnClickListener {
            dismissFragment()
        }

        val btnRoom301: Button = view.findViewById(R.id.btnRoom301)
        val btnRoom302: Button = view.findViewById(R.id.btnRoom302)
        val btnRoom303: Button = view.findViewById(R.id.btnRoom303)
        val btnRoom304: Button = view.findViewById(R.id.btnRoom304)
        val btnRoom305: Button = view.findViewById(R.id.btnRoom305)
        val btnRoom306: Button = view.findViewById(R.id.btnRoom306)
        val btnCompLab: Button = view.findViewById(R.id.btnCompLab)
        val btnMacLab: Button = view.findViewById(R.id.btnMacLab)

        btnRoom301.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 301",
                imageResId = R.drawable.map_room_301
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnRoom302.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 302",
                imageResId = R.drawable.map_room_302
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnRoom303.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 303",
                imageResId = R.drawable.map_room_303
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnRoom304.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 304",
                imageResId = R.drawable.map_room_304
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnRoom305.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 305",
                imageResId = R.drawable.map_room_305
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnRoom306.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 306",
                imageResId = R.drawable.map_room_306
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnCompLab.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Computer Lab",
                imageResId = R.drawable.map_computer_lab
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        btnMacLab.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Mac Lab",
                imageResId = R.drawable.map_mac_lab
            )
            dialog.show(childFragmentManager, "customDialog")
        }
    }

    private fun dismissFragment() {
        // Remove this fragment
        parentFragmentManager.beginTransaction().remove(this).commit()
    }
}