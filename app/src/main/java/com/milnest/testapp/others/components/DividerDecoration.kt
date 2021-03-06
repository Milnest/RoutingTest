package com.milnest.testapp.others.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.milnest.testapp.R

class DividerDecoration : RecyclerView.ItemDecoration {

    private var divider: Drawable? = null
    private var leftMargin = 0
    private var skipLastItemCount: Int = 0

    constructor(context: Context) {
        val styledAttributes = context.obtainStyledAttributes(ATTRS)
        divider = styledAttributes.getDrawable(0)
        styledAttributes.recycle()
    }

    constructor(context: Context, resId: Int, leftMargin: Int) {
        this.leftMargin = leftMargin
        divider = ContextCompat.getDrawable(context, resId)
    }

    constructor(context: Context, resId: Int, leftMargin: Int, skipLastItemCount: Int) {
        this.leftMargin = leftMargin
        this.skipLastItemCount = skipLastItemCount
        divider = ContextCompat.getDrawable(context, resId)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val left = parent.paddingLeft + leftMargin
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        var child: View
        var params: RecyclerView.LayoutParams
        var top: Int
        var bottom: Int

        for (i in 0 until childCount - skipLastItemCount) {
            child = parent.getChildAt(i)
            params = child.layoutParams as RecyclerView.LayoutParams
            top = child.bottom + params.bottomMargin
            bottom = top + divider!!.intrinsicHeight
            divider!!.setBounds(left, top, right, bottom)
            divider!!.draw(c)
        }
    }

    companion object {
        private val ATTRS = intArrayOf(R.drawable.divider)
    }
}
