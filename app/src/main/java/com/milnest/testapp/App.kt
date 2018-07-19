package com.milnest.testapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.milnest.testapp.router.CustomRouter
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Cicerone



class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        cicerone = Cicerone.create(CustomRouter())
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
        private var cicerone: Cicerone<CustomRouter>? = null

        fun getNavigatorHolder(): NavigatorHolder {
            return cicerone!!.navigatorHolder
        }

        fun getRouter(): CustomRouter/*Router*/ {
            return cicerone!!.router
        }
    }
}