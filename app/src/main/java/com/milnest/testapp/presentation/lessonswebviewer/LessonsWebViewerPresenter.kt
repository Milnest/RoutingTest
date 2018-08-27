package com.milnest.testapp.presentation.lessonswebviewer

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.entities.Lesson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

@InjectViewState
class LessonsWebViewerPresenter: MvpPresenter<LessonWebViewerView>() {
    fun loadLessonsList() {
        var link: Element
        val lessonsList: MutableList<Lesson> = ArrayList()
        Observable.just(App.context.getString(R.string.start_android_lessons_page))
                .subscribeOn(Schedulers.io())
                .map{url -> Jsoup.connect(url).get()}
                .map { doc ->
                    for (element in doc.getElementsByClass("list-title")) {
                        link = element.select("a").first()
                        lessonsList.add(Lesson(link.text(), App.context.getString(R.string.start_android_main_page)
                                + link.attr("href")))
                    }
                    lessonsList[0]
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }
}