package com.milnest.testapp.presentation.start

import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.milnest.testapp.App
import com.milnest.testapp.R
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
                R.id.button_to_task_list ->{
                    App.getRouter().navigateTo(FragType.TASK_LIST_MAIN.name)
                }
                R.id.button_to_demo ->{
                    if(App.sharPref.contains(App.APP_PREFERENCES_IS_DEMO)) {
                        if (!App.sharPref.getBoolean(App.APP_PREFERENCES_IS_DEMO, false)){
                            App.sharPref.edit().putBoolean(App.APP_PREFERENCES_IS_DEMO, true).apply()
                            App.newAppComponent(true)
                        }
                        else {
                            App.sharPref.edit().putBoolean(App.APP_PREFERENCES_IS_DEMO, false).apply()
                            App.newAppComponent(false)
                        }
                    }
//                    App.sharPref.edit().putBoolean(App.APP_PREFERENCES_IS_DEMO, true).apply()
                    //--//
//                    App.getRouter().showSystemMessage("Перезапуск", Toast.LENGTH_SHORT)
//                    App.getRouter().exit()
//                    App.getRouter().exit()
                    //--//
//                    App.newAppComponent(true)
                }
            }
        }
    }

}