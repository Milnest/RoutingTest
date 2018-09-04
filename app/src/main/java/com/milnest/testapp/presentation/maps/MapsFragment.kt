package com.milnest.testapp.presentation.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.SupportMapFragment
import com.milnest.testapp.R
import com.milnest.testapp.router.BaseFragment
import com.milnest.testapp.router.FragType
import android.Manifest.permission.ACCESS_FINE_LOCATION
import com.milnest.testapp.presentation.main.MainActivity
import android.content.DialogInterface
import android.R.string.ok




class MapsFragment : BaseFragment(), MapsView{

    @InjectPresenter
    lateinit var presenter: MapsPresenter
    

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onStart() {
        super.onStart()
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS)
            (childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment)
                    .getMapAsync { map -> presenter.initMap(map) }
    }

    override fun getFragType(): FragType {
        return FragType.MAP
    }

    override fun setUpActionBar() {
    }
}
