package com.milnest.testapp.router

import android.os.Bundle
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace

class CustomRouter : BaseRouter() {
    /*private var onNewRootScreenListener: OnNewRootScreenListener? = null
    private var onBackScreenListener: OnBackScreenListener? = null*/

    @JvmOverloads
    fun navigateTo(screenKey: String, data: Any? = null) {
        executeCommand(Forward(screenKey, data))
    }

    @JvmOverloads
    fun newScreenChain(screenKey: String, data: Any? = null) {
        executeCommand(BackTo(null))
        executeCommand(Forward(screenKey, data))
    }

    fun newRootScreen(screenKey: String) {
        newRootScreen(screenKey, null)
    }

    @Synchronized
    fun newRootScreen(screenKey: String, data: Any?) {
        executeCommand(BackTo(null))
        executeCommand(Replace(screenKey, data))

        /*if (onNewRootScreenListener != null)
            onNewRootScreenListener!!.onChangeRootScreen(screenKey)*/
    }

    @JvmOverloads
    fun replaceScreen(screenKey: String, data: Any? = null) {
        executeCommand(Replace(screenKey, data))
    }

    fun backTo(screenKey: String) {
        executeCommand(BackTo(screenKey))
    }

    fun backToWithResult(screenKey: String, result: Bundle) {
        /*if (onBackScreenListener != null)
            onBackScreenListener!!.onBackScreen()*/
        executeCommand(BackToWithResult(screenKey, result))
    }

    fun exit() {
        /*if (onBackScreenListener != null)
            onBackScreenListener!!.onBackScreen()*/
        executeCommand(Back())
    }

    fun exitWithResult(bundle: Bundle) {
        /*if (onBackScreenListener != null)
            onBackScreenListener!!.onBackScreen()*/
        executeCommand(ExitWithResult(bundle))
    }

    fun exitWithMessage(message: String, type: Int) {
        executeCommand(Back())
        executeCommand(CustomSystemMessage(message, type))
    }

    @JvmOverloads
    fun showSystemMessage(message: String, type: Int = STANDARD_MESSAGE_TYPE) {
        executeCommand(CustomSystemMessage(message, type))
    }

    /*fun setOnNewRootScreenListener(onNewRootScreenListener: OnNewRootScreenListener) {
        this.onNewRootScreenListener = onNewRootScreenListener
    }*/

    /*fun setOnBackScreenListener(onBackScreenListener: OnBackScreenListener) {
        this.onBackScreenListener = onBackScreenListener
    }*/

    /*interface OnNewRootScreenListener {
        fun onChangeRootScreen(screenKey: String)
    }*/

    /*interface OnBackScreenListener {
        fun onBackScreen()
    }*/

    companion object {
        val STANDARD_MESSAGE_TYPE = 0
    }
}