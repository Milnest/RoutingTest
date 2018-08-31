package com.milnest.testapp.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.RemoteViews
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.others.eventbus.TaskUpdatedEvent
import com.milnest.testapp.tasklist.data.db.TaskDatabaseHelper
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class TaskWidget : AppWidgetProvider() {

    val UPDATE_ALL_WIDGETS = "update_all_widgets"
    internal val LOG_TAG = "myLogs"
    var context: Context? = null

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.d(LOG_TAG, "onEnabled")
        this.context = context
        EventBus.getDefault().register(this)
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager,
                          appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.d(LOG_TAG, "onUpdate " + Arrays.toString(appWidgetIds))
        for (widgetId in appWidgetIds)
            updateWidget(context, appWidgetManager, widgetId)
    }

    private fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, widgetId: Int) {
        val rvs = RemoteViews(context.getPackageName(), R.layout.task_widget)
        rvs.setTextViewText(R.id.widgetTitle, context.getString(R.string.your_task_title))
        rvs.setTextViewText(R.id.widgetContent, getTaskToShow(context))
        appWidgetManager.updateAppWidget(widgetId, rvs)
    }

    private fun getTaskToShow(context: Context): String {
        val db: SQLiteDatabase = TaskDatabaseHelper(context).writableDatabase
        val cursor = db.query(TaskDatabaseHelper.TABLE, null,
                null, null, null, null, null)
        val indexName = cursor.getColumnIndex(TaskDatabaseHelper.COLUMN_NAME)
        var name: String = context.getString(R.string.no_tasks)
        while (cursor.moveToNext())
            name = cursor.getString(indexName)
        cursor.close()
        return name
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        Log.d(LOG_TAG, "onDeleted " + Arrays.toString(appWidgetIds))
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        Log.d(LOG_TAG, "onDisabled")
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.getAction() == UPDATE_ALL_WIDGETS) {
            val thisAppWidget = ComponentName(
                    context?.getPackageName(), javaClass.name)
            val appWidgetManager = AppWidgetManager
                    .getInstance(context)
            val ids = appWidgetManager.getAppWidgetIds(thisAppWidget)
            for (appWidgetID in ids) {
                context?.let { updateWidget(it,appWidgetManager, appWidgetID) }
            }
        }
    }

    @Subscribe
    fun onTaskUpdateEvent(event: TaskUpdatedEvent) {
        if (event.isUpdate) {
            val intent = Intent(App.context, TaskWidget::class.java)
            intent.setAction(UPDATE_ALL_WIDGETS)
            val pIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            pIntent.send()
        }
    }
}