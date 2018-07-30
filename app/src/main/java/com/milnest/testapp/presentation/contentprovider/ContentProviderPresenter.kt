package com.milnest.testapp.presentation.contentprovider

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.entities.ContactLongInfo
import com.milnest.testapp.entities.ContactShortInfo
import com.milnest.testapp.tasklist.presentation.main.IClickListener

@InjectViewState
class ContentProviderPresenter : MvpPresenter<ContentProviderView>() {
    var adapter: ContactsAdapter? = null

    var lastContactId: Long = -1
    val contentResolver = App.context.contentResolver
    val CONTENT_URI = ContactsContract.Contacts.CONTENT_URI
    val _ID = ContactsContract.Contacts._ID
    val DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
    val HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER
    val PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
    val Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
    val NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER
    val EMAIL_CONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI
    val EMAIL_CONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID
    val EMAIL = ContactsContract.CommonDataKinds.Email.ADDRESS
    val WEBSITE_CONTACT_ID = ContactsContract.CommonDataKinds.Website.CONTACT_ID
    val WEBSITE = ContactsContract.CommonDataKinds.Website.URL


    fun getContactsAdapter(): ContactsAdapter? {
        adapter = ContactsAdapter(contactClickListener)
        adapter?.contactsList = getContactsList()
        return adapter
    }

    val contactClickListener
    get() = object : IClickListener {
        override fun onItemClick(position: Int) {
            val longInfo = getContactInfo(position)
            val longInfoList: MutableList<String> = ArrayList()
            /*longInfoList.add(longInfo.id.toString())*/
            longInfoList.add(longInfo.name)
            longInfoList.add(longInfo.email)
            longInfoList.addAll(longInfo.phone)
            viewState.showContactInfo(longInfoList)
        }

        override fun onItemLongClick(position: Int): Boolean {
            return true
        }
    }

    val acceptListener
    get() = object : View.OnClickListener {
        override fun onClick(view: View?) {
            val contactRes = Intent(ContactsContract.Contacts.EXTRA_ADDRESS_BOOK_INDEX, Uri.parse("content://contacts/people/" + lastContactId))
            viewState.setResult(Activity.RESULT_OK, contactRes)
            viewState.finish()
        }
    }

    private fun getContactInfo(position: Int) : ContactLongInfo {
        val phonesList: MutableList<String> = ArrayList()
        phonesList.add("")
        val contactLongInfo = ContactLongInfo(-1, "", phonesList, "")
        val cursor = contentResolver.query(CONTENT_URI, null,_ID + " = ?", arrayOf(adapter?.getItemId(position).toString()), null)
        if (cursor.moveToNext()) {
            /*val name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME))
            name.toString()*/
            contactLongInfo.id = cursor.getLong(cursor.getColumnIndex(_ID))
            lastContactId = contactLongInfo.id
            val contact_id = contactLongInfo.id.toString()/*cursor.getString(cursor.getColumnIndex(_ID))*/
            val name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME))
            val hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)))
            if (hasPhoneNumber > 0) {
                contactLongInfo.name = name
                val phoneCursor = contentResolver.query(PhoneCONTENT_URI, null,
                        Phone_CONTACT_ID + " = ?", arrayOf(contact_id), null)
                while (phoneCursor.moveToNext()) {
                    contactLongInfo.phone.add(phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER)))
                }
                phoneCursor.close()
            }
            //cursor.getString(cursor.getColumnIndex(EMAIL))
            val emailCursor = contentResolver.query(EMAIL_CONTENT_URI, null,
                    EMAIL_CONTACT_ID + " = ?", arrayOf(contact_id), null)
            if(emailCursor.moveToNext()){
                contactLongInfo.email = emailCursor.getString(emailCursor.getColumnIndex(EMAIL))
            }
            else{
                contactLongInfo.email = App.context.getString(R.string.no_email_value)
            }
            emailCursor.close()
            cursor.close()
        }
        return contactLongInfo
    }

    private fun getContactsList() : MutableList<ContactShortInfo>{
        val contactsList: MutableList<ContactShortInfo> = ArrayList()
        val cursor = contentResolver.query(CONTENT_URI, null,null, null, null)
        while (cursor.moveToNext()){
            val contactShortInfo = ContactShortInfo(-1, "", "")
            contactShortInfo.id = cursor.getLong(cursor.getColumnIndex(_ID))
            val contact_id = contactShortInfo.id.toString()/*cursor.getString(cursor.getColumnIndex(_ID))*/
            val name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME))
            val hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)))
            if (hasPhoneNumber > 0) {
                contactShortInfo.name = name
                val phoneCursor = contentResolver.query(PhoneCONTENT_URI, null,
                        Phone_CONTACT_ID + " = ?", arrayOf(contact_id), null)
                while (phoneCursor.moveToNext()) {
                    /*val phoneNumber*/ contactShortInfo.phone = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER))
                }
                phoneCursor.close()
            }
            if (contactShortInfo.name != "" || contactShortInfo.phone != "")
            contactsList.add(contactShortInfo)
        }
        cursor.close()
        return contactsList
    }
}