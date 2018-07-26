package com.milnest.testapp.presentation.contentprovider

import android.provider.ContactsContract
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.milnest.testapp.App
import com.milnest.testapp.entities.ContactShortInfo
import com.milnest.testapp.tasklist.presentation.main.IClickListener
import java.text.FieldPosition

@InjectViewState
class ContentProviderPresenter : MvpPresenter<ContentProviderView>() {
    var adapter: ContactsAdapter? = null

    val contentResolver = App.context.contentResolver
    val CONTENT_URI = ContactsContract.Contacts.CONTENT_URI
    val _ID = ContactsContract.Contacts._ID
    val DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
    val HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER
    val PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
    val Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
    val NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER
    val anything = ContactsContract.CommonDataKinds.Phone.CONTACT_ID

    fun getContactsAdapter(): ContactsAdapter? {
        adapter = ContactsAdapter(contactClickListener)
        adapter?.contactsList = getContactsList()
        return adapter
    }

    val contactClickListener
    get() = object : IClickListener {
        override fun onItemClick(position: Int) {
            val cursor = contentResolver.query(CONTENT_URI, null,_ID + " = ?", arrayOf(adapter?.getItemId(position).toString()), null)
            if (cursor.moveToNext()) {
                val name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME))
                name.toString()
            }
        }

        override fun onItemLongClick(position: Int): Boolean {
            return true
        }
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
            }
            if (contactShortInfo.name != "" || contactShortInfo.phone != "")
            contactsList.add(contactShortInfo)
        }
        cursor.close()
        return contactsList
    }
}