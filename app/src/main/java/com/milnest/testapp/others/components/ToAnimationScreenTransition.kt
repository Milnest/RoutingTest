package com.milnest.testapp.others.components

import android.support.transition.*
import java.time.Duration

class ToAnimationScreenTransition : TransitionSet() {
    init {
        setOrdering(ORDERING_TOGETHER)
                .addTransition(ChangeBounds())
                .addTransition(ChangeTransform())
                .setDuration(transitionDuration)
    }
    companion object {
        val transitionDuration: Long = 3000
    }
}