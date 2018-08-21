package com.milnest.testapp.presentation.animation

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.milnest.testapp.R
import com.milnest.testapp.presentation.main.MainActivity
import com.milnest.testapp.router.BaseFragment
import com.milnest.testapp.router.FragType
import kotlinx.android.synthetic.main.fragment_anim.*
import kotlinx.android.synthetic.main.fragment_anim_first_state.*

class AnimFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.hide()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_anim, container, false)
    }

    override fun onStart() {
        super.onStart()
        addAnimationOperations()
    }

    private fun addAnimationOperations() {
        var set = false
        val constraint1 = ConstraintSet()
        constraint1.clone(context, R.layout.fragment_anim_first_state)
        val constraint2 = ConstraintSet()
        constraint2.clone(context, R.layout.fragment_anim_second_state)

       anim_floating_button.setOnClickListener{
                TransitionManager.beginDelayedTransition(anim_constraint_first_state)
                val constraint = if(set) constraint1 else constraint2
                constraint.applyTo(anim_constraint_first_state)
                set = !set
        }
    }

    override fun getFragType(): FragType {
        return FragType.ANIMATION
    }
}
