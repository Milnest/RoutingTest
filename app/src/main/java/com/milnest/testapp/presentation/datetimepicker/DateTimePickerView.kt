package com.milnest.testapp.presentation.datetimepicker

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import java.util.*

interface DateTimePickerView : MvpView {
    fun showIinitialDateTime(dateAndTime: Calendar)
    @StateStrategyType(SkipStrategy::class)
    fun showDateDialog(dateAndTime: Calendar)
    @StateStrategyType(SkipStrategy::class)
    fun showTimeDialog(dateAndTime: Calendar)
}
