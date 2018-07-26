package com.milnest.testapp.presentation.contentprovider

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.ContextMenu
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.milnest.testapp.App
import com.milnest.testapp.R
import kotlinx.android.synthetic.main.activity_content_provider.*


class ContentProviderActivity : ContentProviderView, MvpAppCompatActivity() {
    @InjectPresenter
    lateinit var presenter: ContentProviderPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider)
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

}
