package com.milnest.testapp.presentation.start

import android.content.Intent
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.router.FragType

@InjectViewState
class StartPresenter : MvpPresenter<StartView>() {
    val onClickListener: View.OnClickListener
        get() = object : View.OnClickListener {
            override fun onClick(p0: View) { // - ?
                when (p0.id) {
                    R.id.button_to_diag -> {
                        App.getRouter().navigateTo(FragType.DIAGRAM.name)
                    }
                    R.id.button_to_view_pager -> {
                        App.getRouter().navigateTo(FragType.VIEW_PAGER.name)
                    }
                    R.id.button_to_task_list -> {
                        App.getRouter().navigateTo(FragType.TASK_LIST_MAIN.name)
                    }
                    R.id.button_to_demo -> {
                        if (App.isDemoRepository()) {
                            App.sharPref.edit().putBoolean(App.APP_PREFERENCES_IS_DEMO, false).apply()
                        }
                        else{
                            App.sharPref.edit().putBoolean(App.APP_PREFERENCES_IS_DEMO, true).apply()
                        }
                        App.appComponent.dbRep().setIData(App.isDemoRepository())
                    }
                    R.id.button_to_content_provider -> {
                        viewState.startContentProviderActivity()
                    }
                    R.id.button_start_contacts_list -> {
                        viewState.startContactActivity()
                    }
                    R.id.button_to_anim ->{
                        App.getRouter().navigateToWithAnimation((FragType.ANIMATION.name), null,
                                {addSharedElement(p0, p0.transitionName)})
                    }
                }
            }
        }

}