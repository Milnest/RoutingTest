package com.milnest.testapp.presentation.contentprovider

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
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
        button_contact_accept.setOnClickListener(presenter.acceptListener)
    }

    fun setUpDrawerBar(){
        bar = ActionBarDrawerToggle(this, drawer_layout, R.string.drawer_open, R.string.drawer_close)
        drawer_layout.addDrawerListener(bar)
        bar.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        fillRecycler()
    }

    fun fillRecycler(){
        recyclerViewContactsShort.adapter = presenter.getContactsAdapter()
        recyclerViewContactsShort.layoutManager = LinearLayoutManager(App.context)
    }
    override fun showContactInfo(contactInfo: MutableList<String>) {
        /*contactInfoNameTextView.text = contactInfo.name
        contactInfoPhoneTextView.text = contactInfo.phone
        contactInfoEmailTextView.text = contactInfo.email*/
        val adapter = ContactInfoAdapter()
        adapter.contactInfoList = contactInfo
        recyclerViewContactInfo.adapter = adapter
        recyclerViewContactInfo.layoutManager = LinearLayoutManager(App.context)
        drawer_layout.closeDrawers()
        if(intent.action == Intent.ACTION_PICK){
            button_contact_accept.visibility = View.VISIBLE
        }
    }

}
