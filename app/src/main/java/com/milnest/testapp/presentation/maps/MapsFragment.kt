package com.milnest.testapp.presentation.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.milnest.testapp.R
import com.milnest.testapp.presentation.maps.MapsView
import com.milnest.testapp.presentation.maps.MapsPresenter

import com.arellomobile.mvp.MvpFragment
import com.arellomobile.mvp.presenter.InjectPresenter

class MapsFragment : MvpFragment(), MapsView {

    @InjectPresenter
    lateinit var presenter: MapsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    
}
