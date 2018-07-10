package com.milnest.testapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : StartView, MvpAppCompatFragment() {
    @InjectPresenter
    lateinit var presenter: StartPresenter

    lateinit var diagButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        //presenter.setFragmentManager(activity!!.supportFragmentManager)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        bindViews()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        //actBar.setDisplayHomeAsUpEnabled(true)
        return view
    }

    private fun bindViews() {
        diagButton= view!!.findViewById(R.id.button_to_diag)
        diagButton.setOnClickListener(presenter.onClickListener)
        button_to_diag.setOnClickListener(presenter.onClickListener)
        val actBar = (activity as MainActivity).supportActionBar
        actBar?.title = "Start"
        actBar?.setHomeButtonEnabled(true)
        actBar?.setDisplayHomeAsUpEnabled(true)
    }
}

