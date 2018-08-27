package com.milnest.testapp.presentation.lessonswebviewer

import android.content.Context
import android.widget.Toast
import android.webkit.JavascriptInterface
import org.jsoup.Jsoup


class JavaScriptInterface internal constructor(internal var mContext: Context) {

    @JavascriptInterface
    fun showToast(toast: String) {
        val doc = Jsoup.parse(toast)
        val link = doc.select("a").first()
        //link.attr("href")
        //Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
        Toast.makeText(mContext, "link: " + link.attr("href") + "name: " + link.text(), Toast.LENGTH_SHORT).show()
    }
}