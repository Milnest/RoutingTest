package com.milnest.testapp.presentation.maps

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.SupportMapFragment
import com.milnest.testapp.R
import com.milnest.testapp.router.BaseFragment
import com.milnest.testapp.router.FragType
import kotlinx.android.synthetic.main.fragment_maps.*


class MapsFragment : BaseFragment(), MapsView {
    //private var locationManager : LocationManager? = context?.getSystemService(LOCATION_SERVICE) as LocationManager?
    @InjectPresenter
    lateinit var presenter: MapsPresenter

    val permisiionDialog = context?.let {
        AlertDialog.Builder(it)
                .setTitle("Определение местоположения")
                .setMessage("Предоставить приложению доступ к GPS?")
                .setPositiveButton("ОК", presenter.dialogClickListener)
                .create()
    }

    override fun requestGpsPemissions() {
        ActivityCompat.requestPermissions(activity as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION)
    }

    override fun showDialog() {
//        presenter.checkLocationPermission()
        permisiionDialog?.let {
            if (!permisiionDialog.isShowing) {
                permisiionDialog.show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onStart() {
        super.onStart()






        bindViews()
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS)
            (childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment)
                    .getMapAsync { map -> presenter.initMap(map) }
    }

    private fun bindViews() {
        button_stop_tracking.setOnClickListener(presenter.stopTrackingButtonListener)
    }

    override fun sendBroadcast(poke: Intent) {
        context?.sendBroadcast(poke)
    }

    override fun getFragType(): FragType {
        return FragType.MAP
    }

    override fun setUpActionBar() {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        presenter.permissionRequestResultRecieved(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            MapsFragment.GEOLOCATION_SETTINGS_CODE -> presenter.geolocationSettingsClosed()
        }
    }

    override fun checkActivityLocation() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity as Activity,
                        Manifest.permission.ACCESS_FINE_LOCATION))
            showDialog()
        else
            requestGpsPemissions()
    }

    override fun showMessage(messageText: String) {
        Toast.makeText(context, messageText, Toast.LENGTH_SHORT).show()
    }

    companion object {
        val MY_PERMISSIONS_REQUEST_LOCATION = 777
        val GEOLOCATION_SETTINGS_CODE = 0
    }
}
