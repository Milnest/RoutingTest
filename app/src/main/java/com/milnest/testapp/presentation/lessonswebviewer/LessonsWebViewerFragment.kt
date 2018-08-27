package com.milnest.testapp.presentation.lessonswebviewer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.milnest.testapp.R
import com.milnest.testapp.entities.Lesson
import com.milnest.testapp.others.utils.setUpBar
import com.milnest.testapp.router.BaseFragment
import com.milnest.testapp.router.FragType
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class LessonsWebViewerFragment : BaseFragment(), LessonWebViewerView {

    @InjectPresenter
    lateinit var presenter: LessonsWebViewerPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lessons_web_viewer, container, false)
    }

    override fun onStart() {
        super.onStart()
        /*val webSettings = lessons_web_view.getSettings()
        webSettings.setJavaScriptEnabled(true)
        lessons_web_view.loadUrl("https://startandroid.ru/ru/uroki/vse-uroki-spiskom.html")*/
        //lessons_web_view.addJavascriptInterface(JavaScriptInterface(context!!), "AndroidFunction")
        presenter.loadLessonsList()
    }

    override fun getFragType(): FragType {
        return FragType.WEB_VIEW
    }

    override fun setUpActionBar() {
        setUpBar(activity, getString(R.string.web_view_title), true)
    }
}
