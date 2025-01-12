package com.example.ite393exam.map

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.ite393exam.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val intentMapMenuActivity = Intent(this, MapMenuActivity::class.java)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment

        mapFragment?.getMapAsync(this)

        val mapDownButton = findViewById<ImageButton>(R.id.mapDownButton)

        mapDownButton.setOnClickListener {
            startActivity(intentMapMenuActivity)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        val latLng = LatLng(15.969944962416541, 120.57219839114128)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19.5f))
    }

}