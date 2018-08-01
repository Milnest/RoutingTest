package com.milnest.testapp.presentation.contentprovider

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.milnest.testapp.App
import com.milnest.testapp.R
import kotlinx.android.synthetic.main.activity_content_provider.*


class ContentProviderActivity : ContentProviderView, MvpAppCompatActivity() {
    @InjectPresenter
    lateinit var presenter: ContentProviderPresenter

    private lateinit var bar: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this.javaClass.simpleName, "***** fun onCreate")
        setContentView(R.layout.activity_content_provider)
        bindViews()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (drawer_layout.isDrawerOpen(nav_view_left) || drawer_layout.isDrawerOpen(nav_view_right)) {
            drawer_layout.closeDrawers()
        }
        else {
            if (bar.onOptionsItemSelected(item)) return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindViews() {
        setUpDrawerBar()
        button_accept.setOnClickListener(presenter.acceptListener)
    }

    fun setUpDrawerBar(){
        bar = ActionBarDrawerToggle(this, drawer_layout, R.string.drawer_open, R.string.drawer_close)
        drawer_layout.addDrawerListener(bar)
        bar.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        Log.d(this.javaClass.simpleName, "***** fun onStart")
        fillRecycler()
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        Log.d(this.javaClass.simpleName, "***** fun onResume")
    }

    fun fillRecycler(){
        recyclerViewContactsShort.adapter = presenter.getContactsAdapter()
        recyclerViewContactsShort.layoutManager = LinearLayoutManager(App.context)
        recyclerViewEventsShort.adapter = presenter.getMyEventsAdapter()
        recyclerViewEventsShort.layoutManager = LinearLayoutManager(App.context)
    }
    override fun showInfo(info: MutableList<String>) {
        val adapter = InfoAdapter()
        adapter.infoList = info
        recyclerViewContactInfo.adapter = adapter
        recyclerViewContactInfo.layoutManager = LinearLayoutManager(App.context)
        drawer_layout.closeDrawers()
        if(intent.action == Intent.ACTION_PICK){
            button_accept.visibility = View.VISIBLE
        }
    }

    override fun interactProgressBar(show: Boolean) {
        if (show){
            progressBar.visibility = ProgressBar.VISIBLE
        }
        else{
            progressBar.visibility = ProgressBar.GONE
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(this.javaClass.simpleName, "***** fun onPause")
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
        Log.d(this.javaClass.simpleName, "***** fun onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(this.javaClass.simpleName, "***** fun onDestroy")
    }

}
