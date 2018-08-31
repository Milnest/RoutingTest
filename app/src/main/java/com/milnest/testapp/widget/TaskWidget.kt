package com.milnest.testapp.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.RemoteViews
import com.milnest.testapp.R
import com.milnest.testapp.tasklist.data.db.TaskDatabaseHelper
import java.util.*


class TaskWidget : AppWidgetProvider() {

    internal val LOG_TAG = "myLogs"

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.d(LOG_TAG, "onEnabled")
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager,
                          appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.d(LOG_TAG, "onUpdate " + Arrays.toString(appWidgetIds))
        val db: SQLiteDatabase = TaskDatabaseHelper(context).writableDatabase
        val cursor = db.query(TaskDatabaseHelper.TABLE, null,
                TaskDatabaseHelper.COLUMN_ID + "=0", null, null, null, null)
        val indexName = cursor.getColumnIndex(TaskDatabaseHelper.COLUMN_NAME)
        val name: String
        if (cursor.moveToNext())
            name = cursor.getString(indexName)
        else
            name = "nope"
        cursor.close()
        for (widgetId in appWidgetIds){
            val rvs = RemoteViews(context.getPackageName(), R.layout.task_widget)
            rvs.setTextViewText(R.id.widgetTitle, name)
            rvs.setTextViewText(R.id.widgetContent, name)
            appWidgetManager.updateAppWidget(widgetId, rvs)
        }
    }
    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        Log.d(LOG_TAG, "onDeleted " + Arrays.toString(appWidgetIds))
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        Log.d(LOG_TAG, "onDisabled")
    }

}