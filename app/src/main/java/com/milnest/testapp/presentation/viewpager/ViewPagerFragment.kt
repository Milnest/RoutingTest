package com.milnest.testapp.presentation.viewpager

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.milnest.testapp.R
import com.milnest.testapp.router.BaseFragment
import com.milnest.testapp.router.FragType

class ViewPagerFragment : BaseFragment(), ViewPagerView {

    @InjectPresenter
    lateinit var presenter: ViewPagerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var page = layoutInflater.inflate(R.layout.page_layout, null)
        page.findViewById<TextView>(R.id.page_text).setText("PAGE 1")
        presenter.addPage(page, "1")

        page = layoutInflater.inflate(R.layout.page_layout, null)
        page.findViewById<TextView>(R.id.page_text).setText("PAGE 2")
        presenter.addPage(page, "2")

        page = layoutInflater.inflate(R.layout.page_layout, null)
        page.findViewById<TextView>(R.id.page_text).setText("PAGE 3")
        presenter.addPage(page, "3")
        
        return inflater.inflate(R.layout.fragment_view_pager, container, false)
    }

    override fun onStart() {
        super.onStart()
        presenter.setPagerAdapter(view!!.findViewById(R.id.view_pager))
    }

    override fun getFragType(): FragType {
        return  FragType.VIEW_PAGER
    }
}
