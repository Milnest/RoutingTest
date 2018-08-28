package com.milnest.testapp.presentation.lessonswebviewer

import android.content.Intent
import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.entities.Lesson
import com.milnest.testapp.tasklist.presentation.main.IClickListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

@InjectViewState
class LessonsWebViewerPresenter: MvpPresenter<LessonWebViewerView>() {

    var lessonsAdapter: LessonsAdapter = LessonsAdapter(lessonsListener)

    fun loadLessonsList() {
        var link: Element
        val lessonsList: MutableList<Lesson> = ArrayList()
        Observable.just(App.context.getString(R.string.start_android_lessons_page))
                .subscribeOn(Schedulers.io())
                .map{url -> Jsoup.connect(url).get()}
                .map { doc ->
                    run {
                        var index = 0
                        //val temp = doc.getElementsByClass("list-date small")
                        val temp = doc.getElementsByTag("td")
                        val temp1 = temp
                        for (element in doc.getElementsByClass("list-title")) {
                            link = element.select("a").first()
                            index++ //получает соответствующую дату
                            lessonsList.add(Lesson(link.text(), App.context.getString(R.string.start_android_main_page)
                                    + link.attr("href"), doc.getElementsByTag("td")[index].text()/*doc.getElementsByClass("list-date small")[index].text()*/))
                            index++ //пропускает название урока
                        }
                        /*for (element in doc.getElementsByClass("list-date small"))
                        {
                            element.select("td").first().text()
                        }*/
                    }
                    lessonsList
                }
                .map { listOfLessons -> fillAdapter(listOfLessons) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ _ -> viewState.fillLessonsList(lessonsAdapter)}
    }

    private val lessonsListener: IClickListener
    get() = object : IClickListener {
        override fun onItemClick(position: Int) {
            viewState.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(lessonsAdapter.getLessonLink(position))))
        }

        override fun onItemLongClick(position: Int): Boolean {
            return true
        }
    }
    private fun fillAdapter(listOfLessons: MutableList<Lesson>){
        lessonsAdapter.lessonsList = listOfLessons
    }
}