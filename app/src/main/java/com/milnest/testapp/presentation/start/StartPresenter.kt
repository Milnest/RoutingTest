package com.milnest.testapp.presentation.start

import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.router.AppRouter
import com.milnest.testapp.router.FragType

@InjectViewState
class StartPresenter :MvpPresenter<StartView>(){
    val onClickListener: View.OnClickListener
    get() = object : View.OnClickListener {
        override fun onClick(p0: View) { // - ?
            when (p0.id){
                R.id.button_to_diag -> {
                    /*AppRouter.navigateTo(FragType.DIAGRAM)*/
                    App.getRouter().navigateTo(FragType.DIAGRAM.name)
                }
                R.id.button_to_view_pager ->{
                    /*AppRouter.navigateTo(FragType.VIEW_PAGER)*/
                    App.getRouter().navigateTo(FragType.VIEW_PAGER.name)
                }
            }
        }
    }

}