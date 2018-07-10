package com.milnest.testapp

import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class StartPresenter :MvpPresenter<StartView>(){
    val onClickListener: View.OnClickListener
    get() = object : View.OnClickListener {
        override fun onClick(p0: View) { // - ?
            when (p0.id){
                R.id.button_to_diag -> {}
            }
        }
    }

}