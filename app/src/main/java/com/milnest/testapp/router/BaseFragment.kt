package com.milnest.testapp.router

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
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
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(this.javaClass.simpleName, "***** fun onDestroy")
    }
}