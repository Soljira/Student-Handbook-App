package com.example.ite393exam

import android.annotation.SuppressLint
import android.graphics.RenderEffect
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
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

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment

        mapFragment?.getMapAsync(this)

    }

    override fun onMapReady(map: GoogleMap) {
        val latLng = LatLng(15.969944962416541, 120.57219839114128)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19.5f))
    }

}