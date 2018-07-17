package com.milnest.testapp.presentation.splash

import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.milnest.testapp.App
import com.milnest.testapp.router.FragType

@InjectViewState
class SplashPresenter: MvpPresenter<SplashView>() {
    fun show(duration: Long){
        Handler().postDelayed({
            App.getRouter().newRootScreen(FragType.START.name)
            /*AppRouter.newRootScreen(FragType.START)*/
        }, duration)
    }
}