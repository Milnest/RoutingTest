package com.milnest.testapp

import android.support.v4.app.FragmentTransaction
import com.milnest.testapp.router.CustomNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

/*
fun CustomNavigator.forwardCommandAnimated(command: Command, lastScreenKey: String, customTransaction: (FragmentTransaction.() -> Unit)) {
    val forward = command as Forward
    val ft = fragmentManager.beginTransaction()
    ft.customTransaction()
//    forward.customTransaction?.let { ft.it() }
    ft.replace(containerId, createFragment(forward.screenKey, forward.transitionData))
            .addToBackStack(forward.screenKey)
            .commit()
    screenNames.add(command.screenKey)
}*/
