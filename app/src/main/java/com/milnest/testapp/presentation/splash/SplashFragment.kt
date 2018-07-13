package com.milnest.testapp.presentation.splash

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpFragment
import com.arellomobile.mvp.presenter.InjectPresenter

import com.milnest.testapp.R
import com.milnest.testapp.presentation.main.MainActivity
import com.milnest.testapp.router.BaseFragment
import com.milnest.testapp.router.FragType

class SplashFragment : SplashView, BaseFragment() {

    @InjectPresenter
    lateinit var presenter: SplashPresenter

    override fun getFragType(): FragType {
        return FragType.SPLASH
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).supportActionBar!!.hide()
        presenter.show(1000L)
    }
}
