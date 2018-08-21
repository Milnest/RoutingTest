package com.milnest.testapp.router

import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.milnest.testapp.R
import com.milnest.testapp.presentation.animation.AnimFragment
import com.milnest.testapp.presentation.diagram.DiagramFragment
import com.milnest.testapp.presentation.splash.SplashFragment
import com.milnest.testapp.presentation.start.StartFragment
import com.milnest.testapp.presentation.viewpager.ViewPagerFragment
import com.milnest.testapp.tasklist.presentation.list.ListTaskFragment
import com.milnest.testapp.tasklist.presentation.main.TaskListMainFragment
import com.milnest.testapp.tasklist.presentation.text.TextTaskFragment

enum class FragType(name: String) {
    SPLASH(name = "splash"),
    START(name = "start"),
    DIAGRAM(name = "diagram"),
    VIEW_PAGER(name = "view_pager"),
    TASK_LIST_MAIN(name = "task_list_main"),
    TASK_LIST_LIST(name = "task_list_list"),
    TASK_LIST_TEXT(name = "task_list_text"),
    ANIMATION(name = "animation");

    fun createFragment(data: Bundle): BaseFragment = when (this) {
        SPLASH -> SplashFragment()
        START -> StartFragment()
        DIAGRAM -> DiagramFragment()
        VIEW_PAGER -> ViewPagerFragment()
        TASK_LIST_MAIN -> TaskListMainFragment()
        TASK_LIST_LIST -> ListTaskFragment.newInstance(data)
        TASK_LIST_TEXT -> TextTaskFragment.newInstance(data)
        ANIMATION -> AnimFragment()
    }
}

/*
object AppRouter {
    var fragmentManager: FragmentManager? = null
    fun navigateTo(type: FragType) {
        fragmentManager?.let { manager ->
            if (manager.backStackEntryCount == 0) {
                manager.beginTransaction()
                        .add(R.id.container, type.createFragment(), type.name)
                        .addToBackStack(*/
/*null*//*
type.name)
                        .commit()
            } else {
                manager.beginTransaction()
                        .replace(R.id.container, type.createFragment(), type.name)
                        .addToBackStack(*/
/*null*//*
 type.name)
                        .commit()
            }
        }
    }

    fun back() {
        fragmentManager?.let { manager ->
            if (manager.backStackEntryCount > 1) {
                manager.popBackStack()
            }
        }
    }

    fun backTo(type: FragType) {
        while (fragmentManager?.findFragmentByTag(type.name)?.isVisible == false) {
            fragmentManager?.popBackStack()
        }
    }

    fun newRootScreen(type: FragType) {
        while (fragmentManager?.backStackEntryCount ?: 0 > 0) {
            fragmentManager?.popBackStackImmediate()
        }
        fragmentManager?.let { manager ->
            manager.beginTransaction()
                    .add(R.id.container, type.createFragment(), type.name)
                    .addToBackStack(null)
                    .commit()
        }
    }
}*/
