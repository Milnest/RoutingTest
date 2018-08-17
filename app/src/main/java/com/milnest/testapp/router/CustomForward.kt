package com.milnest.testapp.router

import android.support.v4.app.FragmentTransaction
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

internal class CustomForward(screenKey: String, transitionData: Any?, val customTransaction: (FragmentTransaction.() -> Unit)) : Forward(screenKey, transitionData)