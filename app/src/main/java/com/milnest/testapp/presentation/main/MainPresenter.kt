package com.milnest.testapp.presentation.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.presentation.diagram.DiagramFragment
import com.milnest.testapp.presentation.splash.SplashFragment
import com.milnest.testapp.presentation.start.StartFragment
import com.milnest.testapp.presentation.viewpager.ViewPagerFragment
import com.milnest.testapp.router.AppRouter
import com.milnest.testapp.router.FragType
import ru.terrakok.cicerone.android.SupportFragmentNavigator

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    private lateinit var fragmentManager: FragmentManager

    fun setFragmentManager(supportFragmentManager: FragmentManager) {
        fragmentManager = supportFragmentManager
    }

    fun showSplash(){
        /*AppRouter.fragmentManager = fragmentManager
        AppRouter.navigateTo(FragType.SPLASH)*/
        App.getRouter().navigateTo(FragType.SPLASH.name)
    }

    fun backWasPreseed(){
        /*AppRouter.back()*/
        App.getRouter().exit()
    }

    val navigator : SupportFragmentNavigator
        get() = object : SupportFragmentNavigator(fragmentManager, R.id.container) {
            override fun createFragment(screenKey: String?, data: Any?): Fragment {
                when(screenKey){
                    FragType.SPLASH.name -> return SplashFragment()
                    FragType.START.name -> return StartFragment()
                    FragType.DIAGRAM.name -> return DiagramFragment()
                    FragType.VIEW_PAGER.name -> return ViewPagerFragment()
                    else -> throw RuntimeException()
                }
            }

            override fun exit() {
                viewState.finish()
            }

            override fun showSystemMessage(message: String?) {
            }

        }

    fun setNavigator(){
        App.getNavigatorHolder().setNavigator(navigator)
    }

    fun removeNavigator(){
        App.getNavigatorHolder().removeNavigator()
    }
}