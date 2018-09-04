package com.milnest.testapp.presentation.maps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.milnest.testapp.App
import android.location.Criteria
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager



@InjectViewState
class MapsPresenter : MvpPresenter<MapsView>() {
    private var googleMap: GoogleMap? = null

    fun initMap(map: GoogleMap) {
        googleMap = map
        addMarker()
    }

    private fun addMarker() {
        if (ContextCompat.checkSelfPermission(App.context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
            googleMap?.isMyLocationEnabled = true
        val locationManager = App.context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        val location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false))
        val latitude = location.latitude
        val longitude = location.longitude
        val marker = googleMap?.addMarker(MarkerOptions().position(LatLng(latitude, longitude)))
        marker?.title = "Вы здесь"
        marker?.snippet = "широта: $latitude\nдолгота: $longitude"
        marker?.isDraggable = true
        marker?.isFlat = true
        marker?.showInfoWindow()
    }
}
