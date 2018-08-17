package com.milnest.testapp.others.components

import android.support.transition.ChangeBounds
import android.support.transition.ChangeTransform
import android.support.transition.Fade
import android.support.transition.TransitionSet

class ToDiagramTransition : TransitionSet() {
    init {
        setOrdering(ORDERING_TOGETHER)
        addTransition(ChangeBounds())
                .addTransition(ChangeTransform())
                /*.addTransition(ChangeImageTransform())*/
                .addTransition(Fade(Fade.OUT))
    }
}