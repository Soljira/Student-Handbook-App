package com.example.ite393exam.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.example.ite393exam.R

/**
 * A simple [Fragment] subclass.
 * Use the [GroundFloorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroundFloorFragment : Fragment(R.layout.fragment_map_ground_floor) {
    lateinit var dialog : MapCustomDialogFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val closeButton: ImageButton = view.findViewById(R.id.btnBack)
        val canteenButton: Button = view.findViewById(R.id.btnCanteen)
        val isolationRoomButton: Button = view.findViewById(R.id.btnIsolationRoom)
        val commercialSpaceLeftButton: Button = view.findViewById(R.id.btnCommercialSpaceLeft)
        val commercialSpaceRightButton: Button = view.findViewById(R.id.btnCommercialSpaceRight)
        val hydraulicsLabButton: Button = view.findViewById(R.id.btnHydraulicsLab)
        val soilMaterialAndTrainingLabButton: Button = view.findViewById(R.id.btnSoilMaterialAndTrainingLab)
        val incubationRoomButton: Button = view.findViewById(R.id.btnIncubationRoom)
        val mainEntranceButton: Button = view.findViewById(R.id.btnMainEntrance)
        val marketingDeptButton: Button = view.findViewById(R.id.btnMarketingDept)
        val stageButton: Button = view.findViewById(R.id.btnStage)
        val atriumButton: Button = view.findViewById(R.id.btnAtrium)

        closeButton.setOnClickListener {
            dismissFragment()
        }

        canteenButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Canteen",
                imageResId = R.drawable.map_canteen
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        isolationRoomButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Isolation Room",
                imageResId = R.drawable.map_isolation_room
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        commercialSpaceLeftButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "MLS Room",
                imageResId = R.drawable.map_commercial_space_left
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        commercialSpaceRightButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Student Loan Room",
                imageResId = R.drawable.map_commercial_space_right
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        hydraulicsLabButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Hydraulics Lab",
                imageResId = R.drawable.map_hydraulics_lab
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        soilMaterialAndTrainingLabButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Soil Material and Training Lab",
                imageResId = R.drawable.map_soil_material_and_training_lab
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        incubationRoomButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Incubation Room",
                imageResId = R.drawable.map_incubation_room
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        mainEntranceButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Main Entrance",
                imageResId = R.drawable.map_main_entrance
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        marketingDeptButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Marketing Department",
                imageResId = R.drawable.map_marketing_dept
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        stageButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Stage",
                imageResId = R.drawable.map_stage
            )
            dialog.show(childFragmentManager, "customDialog")
        }

        atriumButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Atrium",
                imageResId = R.drawable.map_stage
            )
            dialog.show(childFragmentManager, "customDialog")
        }

    }
    private fun dismissFragment() {
        // Remove this fragment
        parentFragmentManager.beginTransaction().remove(this).commit()
    }
}