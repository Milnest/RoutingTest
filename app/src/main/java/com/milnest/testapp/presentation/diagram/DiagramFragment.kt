package com.milnest.testapp.presentation.diagram

import android.graphics.Canvas
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.milnest.testapp.R
import com.milnest.testapp.presentation.main.MainActivity
import com.milnest.testapp.router.BaseFragment
import com.milnest.testapp.router.FragType
import kotlinx.android.synthetic.main.fragment_diagram.*

class DiagramFragment : DiagramView, BaseFragment() {

    @InjectPresenter
    lateinit var presenter: DiagramPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diagram, container, false)
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).supportActionBar!!.show()
    }

    override fun getFragType(): FragType {
        return FragType.DIAGRAM
    }
}
