package com.milnest.testapp.router

import android.support.v4.app.FragmentTransaction
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

internal class CustomForward(val screenKey: String, val transitionData: Any?, val customTransaction: (FragmentTransaction.() -> Unit)) : Command/*Forward(screenKey, transitionData)*/