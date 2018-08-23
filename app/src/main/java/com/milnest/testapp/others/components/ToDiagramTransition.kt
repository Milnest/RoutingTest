package com.milnest.testapp.others.components

import android.support.transition.*
import java.time.Duration

class ToDiagramTransition : TransitionSet() {
    init {
        setOrdering(ORDERING_TOGETHER)
                .addTransition(ChangeBounds())
                .addTransition(ChangeTransform())
                .setDuration(3000)
    }
}