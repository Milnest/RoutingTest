package com.milnest.testapp.presentation.splash

import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.milnest.testapp.router.AppRouter
import com.milnest.testapp.router.FragType

@InjectViewState
class SplashPresenter: MvpPresenter<SplashView>() {
    fun show(duration: Long){
        Handler().postDelayed({
            AppRouter.newRootScreen(FragType.START)
        }, duration)
    }
}