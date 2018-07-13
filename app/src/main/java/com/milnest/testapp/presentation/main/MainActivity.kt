package com.milnest.testapp.presentation.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.milnest.testapp.R
import com.milnest.testapp.presentation.splash.SplashFragment
import com.milnest.testapp.presentation.start.StartFragment
import com.milnest.testapp.router.AppRouter
import com.milnest.testapp.router.FragType


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
        //router.fragmentManager = supportFragmentManager
        //AppRouter.navigateTo(FragType.SPLASH)
        presenter.showSplash()
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