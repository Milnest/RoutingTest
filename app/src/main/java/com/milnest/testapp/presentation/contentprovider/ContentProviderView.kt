package com.milnest.testapp.presentation.contentprovider

import android.content.Intent
import com.arellomobile.mvp.MvpView
import com.milnest.testapp.entities.InfoItem

interface ContentProviderView : MvpView {
    fun showInfo(info: MutableList<InfoItem>)
    fun setResult(resultCode: Int, data: Intent)
    fun finish()
    fun interactProgressBar(show: Boolean)
}