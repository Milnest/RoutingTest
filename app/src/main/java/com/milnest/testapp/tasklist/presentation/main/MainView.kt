package com.milnest.testapp.tasklist.presentation.main

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface MainView : MvpView{
    fun showActionBar(title : Int)
    fun setSplitIcon(iconResource: Int)
    fun closeActionBar()
    fun startTaskActivity(activityClass: Class<*>?, itemId: Int, actResType: Int)
    @StateStrategyType(SkipStrategy::class)
    fun showNotif(toShow : Int)
    @StateStrategyType(SkipStrategy::class)
    fun startPhotoActivity(cameraIntent: Intent)
    @StateStrategyType(SkipStrategy::class)
    fun createTaskActivity(createTaskIntent : Intent, taskType : Int)
    @StateStrategyType(SkipStrategy::class)
    fun createTaskActivity(taskType: Int, taskClass: Class<*>)
    @StateStrategyType(SkipStrategy::class)
    fun showDialog()
    fun finishActionMode()
}