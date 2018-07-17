package com.milnest.testapp.presentation.main

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.FragmentActivity
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.milnest.testapp.R
import com.milnest.testapp.router.AppRouter


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
        presenter.onCreate(savedInstanceState)
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

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        if (outState != null) {
            presenter.onSaveInstanceState(outState)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1){
            presenter.backWasPreseed()
        } else {
            super.onBackPressed()
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }
}