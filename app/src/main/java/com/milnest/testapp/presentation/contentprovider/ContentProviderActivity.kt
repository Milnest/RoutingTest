package com.milnest.testapp.presentation.contentprovider

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.milnest.testapp.R
import android.provider.ContactsContract
import kotlinx.android.synthetic.main.activity_content_provider.*


class ContentProviderActivity : ContentProviderView, MvpAppCompatActivity() {
    @InjectPresenter
    lateinit var presenter: ContentProviderPresenter

    var CONTENT_URI = ContactsContract.Contacts.CONTENT_URI
    var _ID = ContactsContract.Contacts._ID
    var DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
    var HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER

    var PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
    var Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
    var NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider)
        val cursor = contentResolver.query(CONTENT_URI, null,null, null, null)
        while (cursor.moveToNext()){
            val contact_id = cursor.getString(cursor.getColumnIndex(_ID))
            val name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME))
            val hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)))

            //Получаем имя:
            if (hasPhoneNumber > 0) {
                contact_info_text_view.text = name
                /*output.append("\n Имя: " + name)*/
                val phoneCursor = contentResolver.query(PhoneCONTENT_URI, null,
                Phone_CONTACT_ID + " = ?", arrayOf(contact_id), null)

                //и его номер
                while (phoneCursor.moveToNext()) {
                    val phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER))
                    contact_info_text_view.text = contact_info_text_view.text.toString() + phoneNumber
                    /*output.append("\n Телефон: " + phoneNumber)*/
                }
            }
        }
    }
}
