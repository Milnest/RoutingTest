package com.milnest.testapp.router

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.arellomobile.mvp.MvpAppCompatFragment


abstract class BaseFragment : MvpAppCompatFragment() {
    abstract fun getFragType(): FragType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this.javaClass.simpleName, "***** fun onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d(this.javaClass.simpleName, "***** fun onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(this.javaClass.simpleName, "***** fun onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(this.javaClass.simpleName, "***** fun onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(this.javaClass.simpleName, "***** fun onStop")
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity?.getCurrentFocus()
        if (view == null) {
            view = View(context)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(this.javaClass.simpleName, "***** fun onDestroy")
    }
}