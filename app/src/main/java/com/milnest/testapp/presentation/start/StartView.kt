package com.milnest.testapp.presentation.start

import android.content.Intent
import com.arellomobile.mvp.MvpView

interface StartView : MvpView {
    fun startContentProviderActivity()
    fun startContactActivity()
}