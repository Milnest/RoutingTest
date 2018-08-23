package com.milnest.testapp.others.utils

import android.app.Activity
import android.support.v4.app.FragmentActivity
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.milnest.testapp.R

fun setUpBar(targetActivity: FragmentActivity?, title: String, showHome: Boolean) {
    if (targetActivity?.findViewById<Toolbar>(R.id.toolbar) != null) {
        val toolbar: Toolbar = targetActivity.findViewById(R.id.toolbar)
        if (targetActivity is AppCompatActivity) {
            targetActivity.setSupportActionBar(toolbar)
            val actBar = targetActivity.supportActionBar
            actBar?.title = title
            if (showHome) {
                actBar?.setHomeButtonEnabled(true)
                actBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                actBar?.setHomeButtonEnabled(false)
                actBar?.setDisplayHomeAsUpEnabled(false)
            }
            actBar?.show()
        }
    }
}

fun setUpBar(targetActivity: FragmentActivity?, title: String, showHome: Boolean, bar: Toolbar) {
        val toolbar: Toolbar = bar
        if (targetActivity is AppCompatActivity) {
            targetActivity.setSupportActionBar(toolbar)
            val actBar = targetActivity.supportActionBar
            actBar?.title = title
            if (showHome) {
                actBar?.setHomeButtonEnabled(true)
                actBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                actBar?.setHomeButtonEnabled(false)
                actBar?.setDisplayHomeAsUpEnabled(false)
            }
            actBar?.show()
        }
}