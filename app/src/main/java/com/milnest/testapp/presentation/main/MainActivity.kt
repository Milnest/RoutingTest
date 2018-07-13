package com.milnest.testapp.presentation.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.milnest.testapp.R
import com.milnest.testapp.presentation.diagram.DiagramFragment
import com.milnest.testapp.presentation.splash.SplashFragment
import com.milnest.testapp.presentation.start.StartFragment
import com.milnest.testapp.presentation.viewpager.ViewPagerFragment
import com.milnest.testapp.router.AppRouter
import com.milnest.testapp.router.FragType
import ru.terrakok.cicerone.android.SupportFragmentNavigator


val FragmentActivity?.router: AppRouter
    get() = (this as MainActivity).router

class MainActivity : MainView, MvpAppCompatActivity() {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    val router : AppRouter = AppRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.setFragmentManager(supportFragmentManager)
        presenter.showSplash()
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

    override fun onBackPressed() {
        presenter.backWasPreseed()
    }
}