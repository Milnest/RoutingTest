package com.milnest.testapp.presentation.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.router.CustomNavigator
import com.milnest.testapp.router.FragType
import com.milnest.testapp.tasklist.ID
import com.milnest.testapp.tasklist.presentation.main.TaskListMainFragment
import java.io.Serializable

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    private lateinit var fragmentManager: FragmentManager

    private val navigator: CustomNavigator
        get() = object : CustomNavigator(fragmentManager, R.id.container) {
            override fun createFragment(screenKey: String, data: Any?): Fragment {
                val bundle = Bundle()
                if(data != null) bundle.putInt(ID, (data as Int))
                return FragType.valueOf(screenKey).createFragment(bundle)
            }

            override fun showSystemMessage(message: String, type: Int) {
                //TODO: включить
                //viewState.showMessage(message)
            }

            override fun exit() {
                viewState.finish()
            }
        }


    fun setFragmentManager(supportFragmentManager: FragmentManager) {
        fragmentManager = supportFragmentManager
    }

    fun onCreate(savedInstanceState: Bundle?) {
        /*AppRouter.fragmentManager = fragmentManager
        AppRouter.navigateTo(FragType.SPLASH)*/
        if (savedInstanceState != null) {
            navigator.setScreenNames(savedInstanceState.getSerializable(MainPresenter.STATE_SCREEN_NAMES) as ArrayList<String>)
        } else {
            App.getRouter().navigateTo(FragType.SPLASH.name)
        }

    }

    fun backWasPreseed() {
        if (fragmentManager.backStackEntryCount > 1) {
            App.getRouter().exit()
        } else {
            viewState.finish()
        }
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(STATE_SCREEN_NAMES, navigator.getScreenNames() as Serializable)
    }

    /*val navigator : SupportFragmentNavigator
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

        }*/


    fun setNavigator() {
        App.getNavigatorHolder().setNavigator(navigator)
    }

    fun removeNavigator() {
        App.getNavigatorHolder().removeNavigator()
    }

    companion object {
        private val STATE_SCREEN_NAMES = "state_screen_names"
        private var currentType = FragType.SPLASH
    }
}