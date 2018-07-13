package com.milnest.testapp.presentation.main

import android.support.v4.app.FragmentManager
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.milnest.testapp.R
import com.milnest.testapp.presentation.splash.SplashFragment
import com.milnest.testapp.presentation.start.StartFragment
import com.milnest.testapp.router.AppRouter
import com.milnest.testapp.router.FragType

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    private lateinit var fragmentManager: FragmentManager

    fun setFragmentManager(supportFragmentManager: FragmentManager) {
        fragmentManager = supportFragmentManager
    }

    fun showSplash(){
        AppRouter.fragmentManager = fragmentManager
        AppRouter.navigateTo(FragType.SPLASH)
    }

    fun backWasPreseed(){
        AppRouter.back()
    }
}