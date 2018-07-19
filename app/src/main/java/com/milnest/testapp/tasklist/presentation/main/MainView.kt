package com.milnest.testapp.tasklist.presentation.main

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpView

interface MainView : MvpView{
    fun showActionBar(title : Int)
    fun setSplitIcon(iconResource: Int)
    fun closeActionBar()
    fun startTaskActivity(activityClass: Class<*>?, itemId: Int, actResType: Int)
    fun showNotif(toShow : Int)
    fun startPhotoActivity(cameraIntent: Intent)
    fun createTaskActivity(createTaskIntent : Intent, taskType : Int)
    fun createTaskActivity(taskType: Int, taskClass: Class<*>)
    fun showDialog()
    fun finishActionMode()
}