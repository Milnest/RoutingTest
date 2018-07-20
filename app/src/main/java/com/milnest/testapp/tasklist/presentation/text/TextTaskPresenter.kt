package com.milnest.testapp.tasklist.presentation.text

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.tasklist.ID
import com.milnest.testapp.tasklist.data.repository.DBRepository
import com.milnest.testapp.tasklist.entities.Task
import com.milnest.testapp.tasklist.interactor.TranslateInteractor

@InjectViewState
class TextTaskPresenter : MvpPresenter<TextTaskView>() {
    private var task = Task(Task.TYPE_ITEM_TEXT)
    var translateInteractor : TranslateInteractor? = null

    var rep = App.appComponent.dbRep() //TODO: если не будет работать при смене конфига - перенести в онкриэйт

    fun setStartText(extras: Bundle?) {
        if (extras != null){
            val taskId = extras.getInt(ID)
            task = rep.getTaskById(taskId) as Task
            viewState.setText(task.title, task.data)
        }
    }

    fun saveClicked(title : String, text:String) {
        task.title = title
        task.data = text
        rep.saveTask(task)
        closeView()
    }

    private fun closeView() {
        App.getRouter().exit()
    }
    fun shareClicked(title : String, text:String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, title)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        //taskView.get()?.startShareAct(shareIntent)
        viewState.startShareAct(shareIntent)
    }

    fun translationClicked(title : String, text:String) {
        translateInteractor = TranslateInteractor()
        translateInteractor?.run(title, text, "ru-en") { value, error ->
            if (error == null) {
                viewState.setText(value.text?.get(0), value.text?.get(1))
                viewState.showToast(R.string.translate_completed)
            }
            else{
                //taskView.get()?.showToast(R.string.translate_fail)
                viewState.showToast(R.string.translate_fail)
            }
        }
    }

    /*override fun update(title: String, text: String) {
        if (title == translateInteractor!!.TRANSLATE_FAIL && text == translateInteractor!!.TRANSLATE_FAIL) {
            viewState.showToast(R.string.translate_fail)
        }
        else {
            viewState.setText(title, text)
            viewState.showToast(R.string.translate_completed)
        }
    }*/

    /*fun attachView(taskView: TextTaskView) {
        this.taskView = WeakReference(taskView)
    }*/

}