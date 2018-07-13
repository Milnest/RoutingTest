package com.milnest.testapp.presentation.viewpager

import android.support.v4.view.PagerAdapter
import android.view.View
import android.support.v4.view.ViewPager
import android.view.ViewGroup


class ViewPagerAdapter(val pages: List<Pair<View, String>>): PagerAdapter() {
    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0.equals(p1)
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val v = pages.get(position).first as ViewGroup
        (container as ViewPager).addView(v, 0)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        (container as ViewPager).removeView(view as View)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pages.get(position).second
    }
}