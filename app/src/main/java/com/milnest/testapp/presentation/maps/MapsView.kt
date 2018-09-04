package com.milnest.testapp.presentation.maps

import android.content.Intent
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface MapsView : MvpView {
    fun requestGpsPemissions()
    fun checkActivityLocation()
    @StateStrategyType(SkipStrategy::class)
    fun showMessage(messageText: String)
    @StateStrategyType(SkipStrategy::class)
    fun showDialog()
    fun sendBroadcast(poke: Intent)
    @StateStrategyType(SkipStrategy::class)
    fun startActivityForResult(intent: Intent, geolocatioN_SETTINGS_CODE: Int)
}
