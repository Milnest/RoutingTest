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
import android.util.Log
import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.JointType
//import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.PendingResult
import com.google.maps.model.DirectionsResult
import com.google.maps.model.LatLng
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.presentation.maps.MapsFragment.Companion.MY_PERMISSIONS_REQUEST_LOCATION


@InjectViewState
class MapsPresenter : MvpPresenter<MapsView>() {
    private var googleMap: GoogleMap? = null
    private val apiKey = "AIzaSyD40p1SCzKq1WY7Do-pcQITacWOYp1Qqe0"
    private val coordsList: MutableList<LatLng> = ArrayList()
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
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1f, locationListener)
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 1f, locationListener)
                addMyLocationMarker()
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

    private fun addMyLocationMarker() {
        if (ContextCompat.checkSelfPermission(App.context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
            googleMap?.isMyLocationEnabled = true
        val location = getCurrentLocation()
        location?.let {
            val marker = googleMap?.addMarker(MarkerOptions().position(com.google.android.gms.maps.model.LatLng(location.lat, location.lng)))
            marker?.let {
                marker.title = "Вы были здесь"
                marker.snippet = "широта: ${location.lat}\nдолгота: ${location.lng}"
                marker.isDraggable = true
                marker.isFlat = true
                marker.showInfoWindow()
            }
        }
    }

    private fun addLocationMarker(location: LatLng) {
        if (ContextCompat.checkSelfPermission(App.context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
            googleMap?.isMyLocationEnabled = true
        val marker = googleMap?.addMarker(MarkerOptions().position(com.google.android.gms.maps.model.LatLng(location.lat, location.lng)))
        marker?.let {
            marker.title = "Вы были здесь"
            marker.snippet = "широта: ${location.lat}\nдолгота: ${location.lng}"
            marker.isDraggable = true
            marker.isFlat = true
            marker.showInfoWindow()
        }
    }

    private fun getCurrentLocation(): com.google.maps.model.LatLng? {
        val location = getLastKnownLocation()
        location?.let {
            val latitude = location.latitude
            val longitude = location.longitude
            return LatLng(latitude, longitude)
        }
        return null
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
                        addMyLocationMarker()
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
                else {
                    addMyLocationMarker()
                    /*val locationRequest = LocationRequest.create()
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    locationRequest.setInterval(500)
                    locationRequest.setFastestInterval(100)*/
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0f, locationListener)
                }//addMyLocationMarker()
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
                addMyLocationMarker()
                getCurrentLocation()?.let { coordsList.add(it) }
                //viewState.showMessage("go")
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}
            override fun onProviderEnabled(p0: String?) {}
            override fun onProviderDisabled(p0: String?) {}
        }

    val stopTrackingButtonListener: View.OnClickListener
        get() = object : View.OnClickListener {
            override fun onClick(view: View?) {
//                addLocationMarker(LatLng(55.754724, 37.621380))
//                addLocationMarker(LatLng(55.760133, 37.618697))
//                addLocationMarker(LatLng(55.764753, 37.591313))
//                addLocationMarker(LatLng(55.728466, 37.604155))
//                coordsList.clear()
//                coordsList.add(LatLng(55.754724, 37.621380))
//                coordsList.add(LatLng(55.760133, 37.618697))
//                coordsList.add(LatLng(55.764753, 37.591313))
//                coordsList.add(LatLng(55.728466, 37.604155))
                getLineOnMap(coordsList)
            }

        }

    private fun getLineOnMap(coordList: MutableList<LatLng>) {
        val line = PolylineOptions()
        val latLngBuilder = LatLngBounds.Builder()

        //Проходимся по всем точкам, добавляем их в Polyline и в LanLngBounds.Builder
        for (i in coordList.indices) {
            line.add(com.google.android.gms.maps.model.LatLng(coordList[i].lat, coordList[i].lng))
            latLngBuilder.include(com.google.android.gms.maps.model.LatLng(coordList[i].lat, coordList[i].lng))
        }

        //Делаем линию более менее симпатичное
        line.width(16f).color(R.color.colorPrimary)
        line.jointType(JointType.ROUND)

        //Добавляем линию на карту
        googleMap?.addPolyline(line)
    }
}
