package com.milnest.testapp.others.components

import android.support.transition.*
import java.time.Duration

class ToDiagramTransition : TransitionSet() {
    init {
        setOrdering(ORDERING_SEQUENTIAL)
                .addTransition(ChangeBounds())
                .addTransition(ChangeTransform())
                /*.addTransition(Fade())*/
                .setDuration(3000)
    }
}