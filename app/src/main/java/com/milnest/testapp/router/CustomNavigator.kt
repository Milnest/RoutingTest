package com.milnest.testapp.router

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.*

abstract class CustomNavigator protected constructor(private val fragmentManager: FragmentManager, private val containerId: Int/*, splitListener: OnChangeFragmentListener?*/) : Navigator {
    private var screenNames: MutableList<String> = ArrayList()

    val lastScreenName: String
        get() = if (screenNames.size == 0) FragType.SPLASH.name else screenNames[screenNames.size - 1]

    /*init {
        if (splitListener != null)
            fragmentManager.addOnBackStackChangedListener { splitListener.onChangeFragment() }
    }*/

    @Synchronized override fun applyCommand(command: Command) {
        val lastScreenKey = if (screenNames.isEmpty()) "" else screenNames[screenNames.size - 1]
        when (command) {
            is Forward -> forwardCommand(command, lastScreenKey)
            is Back -> backCommand(command) //TODO: изменено здесь
            is ExitWithResult -> exitWithResultCommand(command)
            is Replace -> replaceCommand(command, lastScreenKey)
            is BackTo -> backToCommand(command)
            is BackToWithResult -> backToWithResultCommand(command)
            is CustomSystemMessage -> {
                customSystemMessageCommand(command)
                return
            }
        }
        printScreensScheme()
    }

    private fun forwardCommand(command: Command, lastScreenKey: String) {
        val forward = command as Forward
        fragmentManager.beginTransaction()
                /*.setCustomAnimations(getEnterAnimation(lastScreenKey, forward.screenKey),
                        getExitAnimation(lastScreenKey, forward.screenKey),
                        getPopEnterAnimation(lastScreenKey, forward.screenKey),
                        getPopExitAnimation(lastScreenKey, forward.screenKey))*/
                .replace(containerId, createFragment(forward.screenKey, forward.transitionData))
                .addToBackStack(forward.screenKey)
                .commit()
        screenNames.add(command.screenKey)
    }

    private fun backCommand(command: Command) {
        if (fragmentManager.backStackEntryCount > 0)
            fragmentManager.popBackStackImmediate()
        else
            exit()

        if (screenNames.size > 0)
            screenNames.removeAt(screenNames.size - 1)
    }

    private fun exitWithResultCommand(command: Command) {
        if (fragmentManager.backStackEntryCount > 0)
            fragmentManager.popBackStackImmediate()
        else
            exit()

        if (screenNames.size > 0)
            screenNames.removeAt(screenNames.size - 1)

        /*if (fragmentManager.backStackEntryCount > 0) {
            val fragment = fragmentManager.findFragmentById(containerId) as FragmentWithResult
            fragment.onFragmentResult((command as ExitWithResult).result)
        }*/
    }

    private fun replaceCommand(command: Command, lastScreenKey: String) {
        val replace = command as Replace
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStackImmediate()
            fragmentManager
                    .beginTransaction()
                    /*.setCustomAnimations(getEnterAnimation(lastScreenKey, replace.screenKey),
                            getExitAnimation(lastScreenKey, replace.screenKey),
                            getPopEnterAnimation(lastScreenKey, replace.screenKey),
                            getPopExitAnimation(lastScreenKey, replace.screenKey))*/
                    .replace(containerId, createFragment(replace.screenKey, replace.transitionData))
                    .addToBackStack(replace.screenKey)
                    .commit()
        } else {
            fragmentManager.popBackStackImmediate()
            fragmentManager
                    .beginTransaction()
                    /*.setCustomAnimations(getEnterAnimation(lastScreenKey, replace.screenKey),
                            getExitAnimation(lastScreenKey, replace.screenKey),
                            getPopEnterAnimation(lastScreenKey, replace.screenKey),
                            getPopExitAnimation(lastScreenKey, replace.screenKey))*/
                    .replace(containerId, createFragment(replace.screenKey, replace.transitionData))
                    .addToBackStack(replace.screenKey)
                    .commit()
        }

        if (screenNames.size > 0)
            screenNames.removeAt(screenNames.size - 1)
        screenNames.add(command.screenKey)
    }

    private fun backToCommand(command: Command) {
        val key = (command as BackTo).screenKey
        if (key == null) {
            backToRoot()
            screenNames.clear()
        } else {
            var hasScreen = false
            for (i in 0 until fragmentManager.backStackEntryCount) {
                if (key == fragmentManager.getBackStackEntryAt(i).name) {
                    fragmentManager.popBackStackImmediate(key, 0)
                    hasScreen = true
                    break
                }
            }
            if (!hasScreen) {
                backToUnexisting()
            }
            if (screenNames.size > 0) {
                screenNames = ArrayList(screenNames.subList(0,
                        fragmentManager.backStackEntryCount + 1))
            }
        }
    }

    private fun backToWithResultCommand(command: Command) {
        val key = (command as BackToWithResult).screenKey as String?
        if (key == null) {
            backToRoot()
            screenNames.clear()
        } else {
            var hasScreen = false
            for (i in 0 until fragmentManager.backStackEntryCount) {
                if (key == fragmentManager.getBackStackEntryAt(i).name) {
                    fragmentManager.popBackStackImmediate(key, 0)
                    hasScreen = true
                    break
                }
            }
            if (!hasScreen) {
                backToUnexisting()
            }
            if (screenNames.size > 0) {
                screenNames = ArrayList(screenNames.subList(0,
                        fragmentManager.backStackEntryCount + 1))
            }
            /*if (fragmentManager.backStackEntryCount > 0) {
                val fragment = fragmentManager.findFragmentById(containerId) as FragmentWithResult
                fragment.onFragmentResult(command.result)
            }*/
        }
    }

    private fun customSystemMessageCommand(command: Command) {
        showSystemMessage((command as CustomSystemMessage).message, command.type)
    }

    private fun printScreensScheme() {
        val str = StringBuilder("[")
        var isFirst = true
        for (name in screenNames) {
            if (isFirst) {
                str.append(name)
                isFirst = false
            } else {
                str.append(" ➔ $name")
            }
        }
        str.append("]")
        Log.d("Cicerone-ext", "Screen chain:$str")
    }

    fun getScreenNames(): List<String> {
        return screenNames
    }

    fun setScreenNames(value: MutableList<String>) {
        screenNames = value
        printScreensScheme()
    }

    private fun backToRoot() {
        //        if (fragmentManager.getBackStackEntryCount() == 0) return;

        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }

        fragmentManager.executePendingTransactions()
    }

    protected abstract fun createFragment(screenKey: String, data: Any?): Fragment

    protected abstract fun showSystemMessage(message: String, type: Int)

    protected abstract fun exit()

    private fun backToUnexisting() {
        backToRoot()
    }

    /*interface OnChangeFragmentListener {
        fun onChangeFragment()
    }*/

    /*@AnimRes
    protected abstract fun getEnterAnimation(oldScreenKey: String, newScreenKey: String): Int

    @AnimRes
    protected abstract fun getExitAnimation(oldScreenKey: String, newScreenKey: String): Int

    @AnimRes
    protected abstract fun getPopEnterAnimation(oldScreenKey: String, newScreenKey: String): Int

    @AnimRes
    protected abstract fun getPopExitAnimation(oldScreenKey: String, newScreenKey: String): Int*/
}