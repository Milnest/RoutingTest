package com.milnest.testapp.others

import com.milnest.testapp.App
import com.milnest.testapp.tasklist.data.repository.AppDataRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(/*var isDemo: Boolean*/) {
//    var newDemoOption = true
    @Provides
    @Singleton
    fun provideIDataRepository (): AppDataRepository/*IDataRepository*/{
        val appDataRep = AppDataRepository()
        appDataRep.setIData(App.isDemoRepository())
        return appDataRep
    }
}
