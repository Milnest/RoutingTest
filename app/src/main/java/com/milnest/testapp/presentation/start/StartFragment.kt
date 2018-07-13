package com.milnest.testapp.presentation.start

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.milnest.testapp.R
import com.milnest.testapp.presentation.main.MainActivity
import com.milnest.testapp.presentation.main.router
import com.milnest.testapp.router.AppRouter
import com.milnest.testapp.router.BaseFragment
import com.milnest.testapp.router.FragType
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : BaseFragment(), StartView {

    @InjectPresenter
    lateinit var presenter: StartPresenter

    lateinit var diagButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        //presenter.setFragmentManager(activity!!.supportFragmentManager)
        super.onCreate(savedInstanceState)
    }

    override fun getFragType(): FragType {
        return FragType.START
    }

    override fun onStart() {
        super.onStart()
        bindViews()

        activity.router //TODO: роутер здесь
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        //actBar.setDisplayHomeAsUpEnabled(true)       
        return view
    }

    private fun bindViews() {
        /*diagButton= view!!.findViewById(R.id.button_to_diag)
        diagButton.setOnClickListener(presenter.onClickListener)*/
        button_to_view_pager.setOnClickListener(presenter.onClickListener)
        button_to_diag.setOnClickListener(presenter.onClickListener)
        val actBar = (activity as MainActivity).supportActionBar
        actBar?.title = "Start"
        actBar?.setHomeButtonEnabled(true)
        actBar?.setDisplayHomeAsUpEnabled(true)
        actBar?.show()
    }
}

