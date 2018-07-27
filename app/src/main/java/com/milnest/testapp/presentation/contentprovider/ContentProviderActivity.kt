package com.milnest.testapp.presentation.contentprovider

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
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
        bar = ActionBarDrawerToggle(this, drawer_layout, R.string.drawer_open, R.string.drawer_close)
        drawer_layout.addDrawerListener(bar)
        bar.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        /*val cursor = contentResolver.query(CONTENT_URI, null,null, null, null)
        while (cursor.moveToNext()){
            val contact_id = cursor.getString(cursor.getColumnIndex(_ID))
            val name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME))
            val hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)))

            //Получаем имя:
            if (hasPhoneNumber > 0) {
                contact_info_text_view.text = name
                *//*output.append("\n Имя: " + name)*//*
                val phoneCursor = contentResolver.query(PhoneCONTENT_URI, null,
                Phone_CONTACT_ID + " = ?", arrayOf(contact_id), null)

                //и его номер
                while (phoneCursor.moveToNext()) {
                    val phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER))
                    contact_info_text_view.text = contact_info_text_view.text.toString() + phoneNumber
                    *//*output.append("\n Телефон: " + phoneNumber)*//*
                }
            }
        }*/
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
        fillRecycler()
    }

    fun fillRecycler(){
        recyclerViewContactsShort.adapter = presenter.getContactsAdapter()
        recyclerViewContactsShort.layoutManager = LinearLayoutManager(App.context)
    }

    override fun onStart() {
        super.onStart()
        bindViews()
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
    }

}
