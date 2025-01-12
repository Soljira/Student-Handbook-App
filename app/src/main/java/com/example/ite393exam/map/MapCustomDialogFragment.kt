package com.example.ite393exam.map

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.ite393exam.R

class MapCustomDialogFragment : DialogFragment() {

    @SuppressLint("UseGetLayoutInflater")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))  // Removes the ugly grey bg
        return inflater.inflate(R.layout.fragment_map_custom_dialog, container, false)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())


        val title = arguments?.getString("title") ?: "Default Title"
        val imageResId = arguments?.getInt("imageResId") ?: R.drawable.map_upang_interior

        val dialogView = LayoutInflater.from(context).inflate(R.layout.fragment_map_custom_dialog, null)
        val imageView = dialogView.findViewById<ImageView>(R.id.imageHolder)
        val titleView = dialogView.findViewById<TextView>(R.id.roomName)
        val btnClose = dialogView.findViewById<ImageButton>(R.id.btnClose)

        // Set the content dynamically
        imageView.setImageResource(imageResId)
        titleView.text = title

        btnClose.setOnClickListener {
            dialog?.dismiss()
        }

        // Set the view to the builder
        builder.setView(dialogView)

        return builder.create()
    }


    // CHANGES THE TEXT AND IMAGE FOR THE DIALOG DEPENDING ON THE BUTTON PRESSED
    companion object {
        fun newInstance(title: String, imageResId: Int): MapCustomDialogFragment {
            val fragment = MapCustomDialogFragment()
            val args = Bundle()
            args.putString("title", title)
            args.putInt("imageResId", imageResId)
            fragment.arguments = args
            return fragment
        }
    }
}