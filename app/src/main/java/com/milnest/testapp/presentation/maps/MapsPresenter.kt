package com.milnest.testapp.presentation.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.v4.content.ContextCompat
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.milnest.testapp.App
import com.milnest.testapp.presentation.maps.MapsFragment.Companion.MY_PERMISSIONS_REQUEST_LOCATION


@InjectViewState
class MapsPresenter : MvpPresenter<MapsView>() {
    private var googleMap: GoogleMap? = null
    val locationManager = App.context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val providers = locationManager.getProviders(true)

    fun initMap(map: GoogleMap) {
        googleMap = map
        if (gpsIsOff())
            turnGpsOn()
        else
            if (isOlderMarshmallow())
                checkLocationPermission()
            else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, locationListener)
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1f, locationListener)
                addMarker()
            }
    }

    private fun gpsIsOff(): Boolean {
        return ((Settings.Secure.getInt(App.context.contentResolver, Settings.Secure.LOCATION_MODE))
                == Settings.Secure.LOCATION_MODE_OFF)
    }

    private fun isOlderMarshmallow(): Boolean {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M
    }

    private fun turnGpsOn() {
        viewState.startActivityForResult(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                MapsFragment.GEOLOCATION_SETTINGS_CODE)
    }

    private fun addMarker() {
        if (ContextCompat.checkSelfPermission(App.context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
            googleMap?.isMyLocationEnabled = true
        val location = getLastKnownLocation()
        location?.let { val latitude = location.latitude
            val longitude = location.longitude
            val marker = googleMap?.addMarker(MarkerOptions().position(LatLng(latitude, longitude)))
            marker?.title = "Вы здесь"
            marker?.snippet = "широта: $latitude\nдолгота: $longitude"
            marker?.isDraggable = true
            marker?.isFlat = true
            marker?.showInfoWindow()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation(): Location? {
        var bestLocation: Location? = null
        for (provider in providers) {
            val l = locationManager.getLastKnownLocation(provider) ?: continue
            if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                // Found best last known location: %s", l);
                bestLocation = l
            }
        }
        return bestLocation
    }

    fun permissionRequestResultRecieved(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // пермишен получен можем работать с locationManager

                    if (ContextCompat.checkSelfPermission(App.context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                        addMarker()
                    }
                    //Request location updates:
                    //locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1f, )

                } else {
                    viewState.showMessage("Местоположение не определено")
                }
//                return
            }
        }
    }

    fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(App.context,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            viewState.checkActivityLocation()
        } else
            viewState.showMessage("Местоположение не определено")
    }

    fun geolocationSettingsClosed() {
        Handler().postDelayed({
            if (!gpsIsOff()) {
                if (isOlderMarshmallow())
                    checkLocationPermission()
                else
                {
                    addMarker()
                    /*val locationRequest = LocationRequest.create()
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    locationRequest.setInterval(500)
                    locationRequest.setFastestInterval(100)*/
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0f, locationListener)
                }//addMarker()
            } else
                viewState.showMessage("Определение местоположения невозможно")
        }
                , 2000)
    }

    val dialogClickListener
        get() = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, id: Int) {
                //Юзер одобрил
                viewState.requestGpsPemissions()
            }
        }
    val locationListener
    get() = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            addMarker()
            viewState.showMessage("go")
        }
        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}
        override fun onProviderEnabled(p0: String?) {}
        override fun onProviderDisabled(p0: String?) {}

    }
    /*val locationListener
        get() = object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                addMarker()
                viewState.showMessage("go")

            }
        }*/
}
