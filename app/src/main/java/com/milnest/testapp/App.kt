package com.milnest.testapp

import android.app.Application
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Cicerone



class App : Application() {

    override fun onCreate() {
        super.onCreate()
        //INSTANCE = this
        cicerone = Cicerone.create()
    }

    companion object {
        //lateinit var INSTANCE: App
        private var cicerone: Cicerone<Router>? = null

        fun getNavigatorHolder(): NavigatorHolder {
            return cicerone!!.navigatorHolder
        }

        fun getRouter(): Router {
            return cicerone!!.router
        }
    }
}