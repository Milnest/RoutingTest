package com.milnest.testapp

import android.app.Application
import com.milnest.testapp.router.CustomRouter
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Cicerone



class App : Application() {

    override fun onCreate() {
        super.onCreate()
        //INSTANCE = this
        cicerone = Cicerone.create(CustomRouter())
    }

    companion object {
        //lateinit var INSTANCE: App
       /* private var cicerone: Cicerone<Router>? = null*/
        private var cicerone: Cicerone<CustomRouter>? = null

        fun getNavigatorHolder(): NavigatorHolder {
            return cicerone!!.navigatorHolder
        }

        fun getRouter(): CustomRouter/*Router*/ {
            return cicerone!!.router
        }
    }
}