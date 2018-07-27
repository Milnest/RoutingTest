package com.milnest.testapp.presentation.contentprovider

import com.arellomobile.mvp.MvpView

interface ContentProviderView : MvpView {
    fun showContactInfo(contactInfo: MutableList<String>)
}