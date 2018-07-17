package com.milnest.testapp.presentation.viewpager

import android.support.v4.view.ViewPager
import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter


@InjectViewState
class ViewPagerPresenter: MvpPresenter<ViewPagerView>() {
    val pages: MutableList<Pair<View, String>> = ArrayList()

    fun addPage(page: View?, pageTitle: String) {
        if (page != null) {
            pages.add(Pair(page,pageTitle))
        }
    }

    fun setPagerAdapter(viewPager: ViewPager?) {
        //viewPager?.offscreenPageLimit = 3
        viewPager?.adapter = ViewPagerAdapter(pages)
        viewPager?.currentItem = 0
    }

    fun clearPageList() {
        pages.clear()
    }
    /*fun addPage(){
        val page = inflater.inflate(R.layout.page, null)
        val textView = page.findViewById(R.id.text_view) as TextView
        textView.text = "Страница 1"
        pages.add(page)
    }*/
}