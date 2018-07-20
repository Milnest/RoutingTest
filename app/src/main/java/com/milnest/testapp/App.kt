package com.milnest.testapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.milnest.testapp.others.AppComponent
import com.milnest.testapp.others.AppModule
import com.milnest.testapp.others.DaggerAppComponent
import com.milnest.testapp.router.CustomRouter
import dagger.android.support.DaggerApplication
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Cicerone



class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        cicerone = Cicerone.create(CustomRouter())
        appComponent = DaggerAppComponent.builder().appModule(AppModule(getMySharPref())).build()
    }

    private fun getMySharPref(): Boolean {
        sharPref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        if(sharPref.contains(APP_PREFERENCES_IS_DEMO)) {
            return sharPref.getBoolean(APP_PREFERENCES_IS_DEMO, false)
        }
        return false
    }

    companion object {
        lateinit var appComponent: AppComponent
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
        private var cicerone: Cicerone<CustomRouter>? = null

        val APP_PREFERENCES = "testapp_settings"
        val APP_PREFERENCES_IS_DEMO = "is_demo"
        lateinit var sharPref: SharedPreferences
        fun getNavigatorHolder(): NavigatorHolder {
            return cicerone!!.navigatorHolder
        }

        fun getRouter(): CustomRouter/*Router*/ {
            return cicerone!!.router
        }

        fun newAppComponent(isDemo: Boolean){
            appComponent = DaggerAppComponent.builder().appModule(AppModule(isDemo)).build()
        }
    }
}