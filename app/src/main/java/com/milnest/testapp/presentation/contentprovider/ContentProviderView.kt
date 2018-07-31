package com.milnest.testapp.presentation.contentprovider

import android.content.Intent
import com.arellomobile.mvp.MvpView

interface ContentProviderView : MvpView {
    fun showInfo(info: MutableList<String>)
    fun setResult(resultCode: Int, data: Intent)
    fun finish()
    fun interactProgressBar(show: Boolean)
}