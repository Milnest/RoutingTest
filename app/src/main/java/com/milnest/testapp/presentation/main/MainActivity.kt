package com.milnest.testapp.presentation.main

import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.support.v4.app.FragmentActivity
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.SupportMapFragment
import com.milnest.testapp.R

class MainActivity : MainView, MvpAppCompatActivity() {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.setFragmentManager(supportFragmentManager)
        presenter.onCreate(savedInstanceState)
        /*if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS)
            Handler().postDelayed({
                (fragmentManager?.findFragmentById(R.id.mapFrag) as MapFragment)
                        .getMapAsync { map ->  }
            }, 3000)*/
    }

    override fun onResume() {
        super.onResume()
        presenter.setNavigator()
    }

    override fun onPause() {
        super.onPause()
        presenter.removeNavigator()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> presenter.backWasPreseed()
        }
        super.onOptionsItemSelected(item)
        return true
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        if (outState != null) {
            presenter.onSaveInstanceState(outState)
        }
    }

    override fun onBackPressed() {
        presenter.backWasPreseed()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }
}