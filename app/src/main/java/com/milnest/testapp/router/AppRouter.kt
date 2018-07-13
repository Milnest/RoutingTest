package com.milnest.testapp.router

import android.support.v4.app.FragmentManager
import com.milnest.testapp.R
import com.milnest.testapp.presentation.diagram.DiagramFragment
import com.milnest.testapp.presentation.splash.SplashFragment
import com.milnest.testapp.presentation.start.StartFragment
import com.milnest.testapp.presentation.viewpager.ViewPagerFragment

enum class FragType(name: String){
    SPLASH(name = "splash"),
    START(name = "start"),
    DIAGRAM(name = "diagram"),
    VIEW_PAGER(name = "view_pager");
    fun createFragment(): BaseFragment = when (this){
            SPLASH -> SplashFragment()
            START -> StartFragment()
            DIAGRAM -> DiagramFragment()
            VIEW_PAGER -> ViewPagerFragment()
        }
    }

object AppRouter {
    var fragmentManager: FragmentManager? = null
    fun navigateTo(type: FragType){
       fragmentManager?.let {manager ->
           if (manager.backStackEntryCount == 0){
               manager.beginTransaction()
                       .add(R.id.container, type.createFragment(), type.name)
                       .addToBackStack(/*null*/type.name)
                       .commit()
           } else {
               manager.beginTransaction()
                       .replace(R.id.container, type.createFragment(), type.name)
                       .addToBackStack(/*null*/ type.name)
                       .commit()
           }
       }
    }
    fun back(){
        fragmentManager?.let { manager ->
            if (manager.backStackEntryCount > 1){
                manager.popBackStack()
            }
        }
    }

    fun backTo(type: FragType){
        while (fragmentManager?.findFragmentByTag(type.name)?.isVisible == false){
            fragmentManager?.popBackStack()
        }
    }

    fun newRootScreen(type: FragType) {
        while(fragmentManager?.backStackEntryCount ?: 0 > 0) {
            fragmentManager?.popBackStackImmediate()
        }
        fragmentManager?.let { manager ->
            manager.beginTransaction()
                    .add(R.id.container, type.createFragment(), type.name)
                    .addToBackStack(null)
                    .commit()
        }
    }
}