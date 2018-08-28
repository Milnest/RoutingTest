package com.milnest.testapp.presentation.lessonswebviewer

import android.content.Intent
import com.arellomobile.mvp.MvpView

interface LessonWebViewerView: MvpView {
    fun fillLessonsList(lessonsAdapter: LessonsAdapter)
    fun startActivity(intent: Intent?)
}