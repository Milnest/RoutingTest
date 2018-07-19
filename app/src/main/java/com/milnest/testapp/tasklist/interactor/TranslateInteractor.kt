package com.milnest.testapp.tasklist.interactor

import com.google.gson.GsonBuilder
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.tasklist.data.web.TranslateData
import com.milnest.testapp.tasklist.data.web.YANDEX_API
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TranslateInteractor{
    val TRANSLATE_FAIL = App.context.resources.getString(R.string.translate_fail)
    private val gson = GsonBuilder().create()

    private var disposable: Disposable? = null

    fun run(title: String, text: String, transDirection: String, function: (TranslateData, Throwable?) -> Unit)  {
       val operation = YANDEX_API.translate("trnsl.1.1.20180420T121109Z.b002d3187929b557" +
                 ".b397db53cb8218077027dca1b19ad897ee594788", arrayListOf(title,text), transDirection)
        disposable = operation
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(function)/*operation.subscribe(function)*/
    }

    fun dispose(){
        disposable?.dispose()
    }
}
