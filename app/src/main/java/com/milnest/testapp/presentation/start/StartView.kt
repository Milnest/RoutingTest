package com.milnest.testapp.presentation.start

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface StartView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun startContentProviderActivity()
    fun startContactActivity()
}