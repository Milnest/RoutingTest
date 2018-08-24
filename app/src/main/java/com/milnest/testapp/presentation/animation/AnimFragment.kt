package com.milnest.testapp.presentation.animation

import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintSet
import android.support.design.widget.AppBarLayout
import android.support.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.milnest.testapp.R
import com.milnest.testapp.others.components.ToAnimationScreenTransition
import com.milnest.testapp.others.utils.setUpBar
import com.milnest.testapp.presentation.main.MainActivity
import com.milnest.testapp.router.BaseFragment
import com.milnest.testapp.router.FragType
import kotlinx.android.synthetic.main.anim_toolbar.*
import kotlinx.android.synthetic.main.fragment_anim.*
import kotlinx.android.synthetic.main.fragment_anim_first_state.*

class AnimFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_anim, container, false)
    }

    override fun onStart() {
        super.onStart()




        (activity as MainActivity?)?.findViewById<AppBarLayout>(R.id.start_appbar)?.visibility = View.GONE
        setUpActionBar()
        addAnimationOperations()
        //Показать floating_button  в то же время, когда закончится анимация всплытия
        Handler().postDelayed({
            anim_floating_button.visibility = View.VISIBLE
        }, ToAnimationScreenTransition.transitionDuration)
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

    override fun setUpActionBar() {
        setUpBar(activity, getString(R.string.animation_title), true, toolbar)
    }

    override fun getFragType(): FragType {
        return FragType.ANIMATION
    }
}
