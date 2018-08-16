package com.milnest.testapp.presentation.contentprovider

import android.content.Intent
import com.arellomobile.mvp.MvpView
import com.milnest.testapp.entities.ContactLongInfo

interface ContentProviderView : MvpView {
    fun showInfo(info: ContactLongInfo)
    fun setResult(resultCode: Int, data: Intent)
    fun finish()
    fun interactProgressBar(show: Boolean)
    fun startActivity(callIntent: Intent)
    fun showEvents()
    fun hideEvents()
    fun showContacts()
    fun hideContacts()
}