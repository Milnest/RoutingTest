package com.milnest.testapp.others

import com.milnest.testapp.tasklist.data.repository.DBRepository
import com.milnest.testapp.tasklist.data.repository.DemoRepository
import com.milnest.testapp.tasklist.data.repository.IDataRepository
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class AppModule(var isDemo: Boolean) {
    var newDemoOption = true

    @Provides
    @Singleton
    fun provideIDataRepository (): IDataRepository{
        /*if (!demo) return DBRepository()
        else return DemoRepository()*/
        if (!isDemo) return DBRepository()
        else return DemoRepository()
    }
}
