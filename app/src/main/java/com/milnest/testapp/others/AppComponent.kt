package com.milnest.testapp.others

import com.milnest.testapp.tasklist.data.repository.IDataRepository
import com.milnest.testapp.tasklist.presentation.list.ListTaskPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun dbRep(): IDataRepository
    
    /*fun inject(listTaskPresenter: ListTaskPresenter)*/
}
