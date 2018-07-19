package com.milnest.testapp.tasklist.presentation.text

import android.content.Intent
import com.arellomobile.mvp.MvpView

interface TextTaskView: MvpView {
    fun setText(title: String?, text: String?)
    fun startShareAct(shareIntent: Intent)
    fun showToast(toShow : Int)
}