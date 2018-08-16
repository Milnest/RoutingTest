package com.milnest.testapp.presentation.contentprovider

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.provider.ContactsContract
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.widget.RadioGroup
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.entities.*
import com.milnest.testapp.others.eventbus.LoaderShowEvent
import com.milnest.testapp.others.utils.RegexPicker
import com.milnest.testapp.tasklist.presentation.main.IClickListener
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@InjectViewState
class ContentProviderPresenter : MvpPresenter<ContentProviderView>() {
    var adapter: ContactsAdapter? = null
    var eventsAdapter: EventsAdapter? = null

    private var lastContactId: Long = -1
    private var lastPosition: Int = 0
    val contentResolver = App.context.contentResolver
    val CONTENT_URI = ContactsContract.Contacts.CONTENT_URI
    val _ID = ContactsContract.Contacts._ID
    val DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
    //    val CONTACT_PHOTO = ContactsContract.Contacts.Photo.PHOTO_URI
    val CONTACT_PHOTO = ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
    val HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER
    val PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
    val Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
    val NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER
    val EMAIL_CONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI
    val EMAIL_CONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID
    val EMAIL = ContactsContract.CommonDataKinds.Email.ADDRESS
    val EVENTS_CONTENT_URI = CalendarContract.Events.CONTENT_URI
    val EVENT_ID = CalendarContract.Events._ID
    val EVENT_TITLE = CalendarContract.Events.TITLE
    val EVENT_DESC = CalendarContract.Events.DESCRIPTION

    init {
        adapter = ContactsAdapter(contactClickListener, contactWithoutPhotoClickListener)
    }

    fun getMyEventsAdapter(): EventsAdapter? {
        eventsAdapter = EventsAdapter(eventClickListener)
        eventsAdapter?.eventsList = getEventsList()
        return eventsAdapter
    }

    val eventClickListener
        get() = object : IClickListener {
            override fun onItemClick(position: Int) {
                val info = getEventInfo(position)
                //TODO: сделать что-то
                /*val infoList: MutableList<String> = ArrayList()
                infoList.add(InfoItem(info.title, InfoItem.INFO_TYPE_TEXT))
                infoList.add(InfoItem(info.content, InfoItem.INFO_TYPE_TEXT))
                viewState.showInfo(infoList)*/
            }

            override fun onItemLongClick(position: Int): Boolean {
                return true
            }
        }

    fun getContactsAdapter(): ContactsAdapter? {
        //viewState.interactProgressBar(true)
        EventBus.getDefault().post(LoaderShowEvent(true))
        Single.create<MutableList<ContactShortInfo>> {
            it.onSuccess(getContactsList())
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list, throwable ->
                    if (throwable == null)
                        adapter?.contactsList = list
                    /*viewState.interactProgressBar(false)*/
                    EventBus.getDefault().post(LoaderShowEvent(false))
                }
        return adapter
    }

    val contactClickListener
        get() = object : IClickListener {
            override fun onItemClick(position: Int) {
                val longInfo = getContactInfo(position)
                viewState.showInfo(longInfo)
            }

            override fun onItemLongClick(position: Int): Boolean {
                return true
            }
        }

    val contactWithoutPhotoClickListener
    get() = object : ContactsAdapter.ContactWithoutPhotoClickListener {
        override fun onItemClick(position: Int, color: Int) {
            val longInfo = getContactInfo(position, color)
            viewState.showInfo(longInfo)
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

    val phoneListener
        get() = object : InfoAdapter.PhoneClickListener {
            override fun onItemClick(phoneNumber: String) {
                val callIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
                viewState.startActivity(callIntent)
            }
        }

    val showButtonListener
    get() = object : View.OnClickListener {
        override fun onClick(view: View) {
            when(view.id){
                R.id.button_show_events -> {
                    viewState.hideContacts()
                    viewState.showEvents()
                }
                R.id.button_show_contacts -> {
                    viewState.hideEvents()
                    viewState.showContacts()
                }
            }
        }
    }

    val drawerListener
    get() = object : DrawerLayout.DrawerListener {
        override fun onDrawerStateChanged(newState: Int) {

        }

        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

        }

        override fun onDrawerClosed(drawerView: View) {

        }

        override fun onDrawerOpened(drawerView: View) {
            if (drawerView.id == R.id.nav_view_right)
                viewState.scrollToLast(lastPosition)
        }

    }

    private fun getContactInfo(position: Int): ContactLongInfo {
        lastPosition = position
        val infoList: MutableList<Info> = ArrayList()
        /*phonesList.add("")*/
        val contactLongInfo = ContactLongInfo(-1, infoList, Info(Info.TYPE_CONTACT_PHOTO_HOLDER, ""))
        val cursor = contentResolver.query(CONTENT_URI, null, _ID + " = ?", arrayOf(adapter?.getItemId(position).toString()), null)
        if (cursor.moveToNext()) {
            contactLongInfo.id = cursor.getLong(cursor.getColumnIndex(_ID))
            lastContactId = contactLongInfo.id
            val contact_id = contactLongInfo.id.toString()
            val name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME))
            contactLongInfo.info.add(Info(Info.TYPE_CONTACT_NAME, name))
            //*******//
            val photo = cursor.getString(cursor.getColumnIndex(CONTACT_PHOTO))
            if (photo != null) contactLongInfo.photo = Info(Info.TYPE_CONTACT_PHOTO, photo)
            else contactLongInfo.photo = Info(Info.TYPE_CONTACT_PHOTO_HOLDER, RegexPicker.getInitials(name))
            //*******//
            val hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)))
            if (hasPhoneNumber > 0) {
                val phoneCursor = contentResolver.query(PhoneCONTENT_URI, null,
                        Phone_CONTACT_ID + " = ?", arrayOf(contact_id), null)
                while (phoneCursor.moveToNext()) {
                    contactLongInfo.info.add(Info(Info.TYPE_CONTACT_PHONE, phoneCursor.getString(
                            phoneCursor.getColumnIndex(NUMBER))))
                }
                phoneCursor.close()
            }
            val emailCursor = contentResolver.query(EMAIL_CONTENT_URI, null,
                    EMAIL_CONTACT_ID + " = ?", arrayOf(contact_id), null)
            if (emailCursor.moveToNext()) {
                contactLongInfo.info.add(Info(Info.TYPE_CONTACT_EMAIL,
                        emailCursor.getString(emailCursor.getColumnIndex(EMAIL))))
            } else {
                contactLongInfo.info.add(Info(Info.TYPE_CONTACT_EMAIL, App.context.getString(R.string.no_email_value)))
            }
            emailCursor.close()
            cursor.close()
        }
        return contactLongInfo
    }

    private fun getContactInfo(position: Int, color: Int): ContactLongInfo{
        val info = getContactInfo(position)
        info.color = color
        return info
    }

    private fun getPlaceHolderLiteral(name: String?): String {
        val nameLiteral: String
        if (name?.take(1) != null) nameLiteral = name.take(1)
        else nameLiteral = ""
        return nameLiteral
    }

    private fun getContactsList(): MutableList<ContactShortInfo> {
        val contactsList: MutableList<ContactShortInfo> = ArrayList()
        var lastLiteral = ""
        val cursor = contentResolver.query(CONTENT_URI, null, null, null, DISPLAY_NAME + " ASC")
        while (cursor.moveToNext()) {
            val contactShortInfo = ContactShortInfo(-1, "", "", "",
                    ContactShortInfo.SHORT_INFO_PHOTO_PLACEHOLDER)
            contactShortInfo.id = cursor.getLong(cursor.getColumnIndex(_ID))
            val contact_id = contactShortInfo.id.toString()/*cursor.getString(cursor.getColumnIndex(_ID))*/
            val name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME))
            val hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)))
            if (hasPhoneNumber > 0) {
                contactShortInfo.name = name
                val phoneCursor = contentResolver.query(PhoneCONTENT_URI, null,
                        Phone_CONTACT_ID + " = ?", arrayOf(contact_id), null)
                while (phoneCursor.moveToNext()) {
                    contactShortInfo.phone = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER))
                }
                phoneCursor.close()
            }
            val photo = cursor.getString(cursor.getColumnIndex(CONTACT_PHOTO))
            if (photo != null) {
                contactShortInfo.photoUriString = photo
                contactShortInfo.type = ContactShortInfo.SHORT_INFO_PHOTO
            } else {
                /*RegexPicker.getInitials(name)*/
                val regexName = RegexPicker.getInitials(name)
                contactShortInfo.photoUriString = regexName/*RegexPicker.getInitials(name)*//*getPlaceHolderLiteral(name)*/
                contactShortInfo.type = ContactShortInfo.SHORT_INFO_PHOTO_PLACEHOLDER
            }
            if (contactShortInfo.name != "" || contactShortInfo.phone != "") {
                val tempLiteral = getPlaceHolderLiteral(name)
                if (lastLiteral != tempLiteral){
                    contactsList.add(ContactShortInfo(-1, tempLiteral, "", "",
                            ContactShortInfo.SHORT_INFO_GROUP))
                    lastLiteral = tempLiteral
                    lastLiteral.length
                }
                contactsList.add(contactShortInfo)
            }
        }
        cursor.close()
        return contactsList
    }

    fun getEventsList(): MutableList<EventShortInfo> {
        val eventsList: MutableList<EventShortInfo> = ArrayList()
        val cursor = contentResolver.query(EVENTS_CONTENT_URI, null, null, null, null)
        while (cursor.moveToNext()) {
            val eventShortInfo = EventShortInfo(-1, "", "")
            eventShortInfo.id = cursor.getLong(cursor.getColumnIndex(EVENT_ID))
            eventShortInfo.title = cursor.getString(cursor.getColumnIndex(EVENT_TITLE))
            eventShortInfo.content = if (cursor.getString(cursor.getColumnIndex(EVENT_DESC)) != null) {
                cursor.getString(cursor.getColumnIndex(EVENT_DESC))
            } else {
                ""
            }
            eventsList.add(eventShortInfo)
        }
        cursor.close()
        return eventsList
    }

    private fun getEventInfo(position: Int): EventShortInfo {
        val eventInfo = EventShortInfo(-1, "", "")
        val cursor = contentResolver.query(EVENTS_CONTENT_URI, null, _ID + " = ?",
                arrayOf(eventsAdapter?.getItemId(position).toString()), null)
        if (cursor.moveToNext()) {
            eventInfo.id = cursor.getLong(cursor.getColumnIndex(EVENT_ID))
            eventInfo.title = cursor.getString(cursor.getColumnIndex(EVENT_TITLE))
            eventInfo.content = if (cursor.getString(cursor.getColumnIndex(EVENT_DESC)) != null) {
                cursor.getString(cursor.getColumnIndex(EVENT_DESC))
            } else {
                ""
            }
        }
        cursor.close()
        return eventInfo
    }

    fun onStop() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
    }

    fun onStart() {
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoaderShowEvent(event: LoaderShowEvent) {
        viewState.interactProgressBar(event.progressBarState)
    }
}