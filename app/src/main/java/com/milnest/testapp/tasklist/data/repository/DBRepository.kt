package com.milnest.testapp.tasklist.data.repository

import android.content.ContentValues
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.milnest.testapp.tasklist.entities.Task
import com.milnest.testapp.App
import com.milnest.testapp.tasklist.data.db.TaskDatabaseHelper

/**
 * Created by t-yar on 21.04.2018.
 */

class DBRepository : IDataRepository{
    private var db: SQLiteDatabase = TaskDatabaseHelper(App.context).writableDatabase

    override fun setIData(isDemo: Boolean) = Unit

    override fun getIData(): IDataRepository = this

    override fun getAllTasks(): MutableList<Task> {
        val cursor = db.query(TaskDatabaseHelper.TABLE, null,
                null, null, null, null, null)
        val list = cursorToList(cursor)
        cursor.close()
        return list
    }

    private fun cursorToList(cursor: Cursor): MutableList<Task> {
        val taskList: MutableList<Task> = ArrayList()
        val indexId = cursor.getColumnIndex(TaskDatabaseHelper.COLUMN_ID)
        val indexName = cursor.getColumnIndex(TaskDatabaseHelper.COLUMN_NAME)
        val indexContent = cursor.getColumnIndex(TaskDatabaseHelper.COLUMN_CONTENT)
        val indexType = cursor.getColumnIndex(TaskDatabaseHelper.COLUMN_TYPE)
        var id: Int
        var name: String
        var type: Int
        var content: String

        while (cursor.moveToNext()) {
            id = cursor.getInt(indexId)
            name = cursor.getString(indexName)
            type = cursor.getInt(indexType)
            content = cursor.getString(indexContent)

            taskList.add(Task(id, name, type, content))
        }
        return taskList
    }

    override fun addTask(name: String, type: Int, content: String) {
        try {
            val cv = ContentValues()
            cv.put(TaskDatabaseHelper.COLUMN_NAME, name)
            cv.put(TaskDatabaseHelper.COLUMN_TYPE, type)
            cv.put(TaskDatabaseHelper.COLUMN_CONTENT, content)
            db.insert(TaskDatabaseHelper.TABLE, TaskDatabaseHelper.COLUMN_ID, cv)
        } catch (e: SQLException) {
            Log.e("ERROR", e.toString())
        }

    }

    override fun getTaskById(id: Int): Task? {
        try {
            val cursor = db.query(TaskDatabaseHelper.TABLE, null,
                    TaskDatabaseHelper.COLUMN_ID + "=$id", null, null, null, null)

            val indexName = cursor.getColumnIndex(TaskDatabaseHelper.COLUMN_NAME)
            val indexType = cursor.getColumnIndex(TaskDatabaseHelper.COLUMN_TYPE)
            val indexContent = cursor.getColumnIndex(TaskDatabaseHelper.COLUMN_CONTENT)

            var name = ""
            var content = ""
            var type = -1
            if (cursor.moveToNext()) {
                name = cursor.getString(indexName)
                content = cursor.getString(indexContent)
                type = cursor.getInt(indexType)
            }
            cursor.close()
            return Task(id, name, type, content)
        } catch (ex : SQLException){
            return null
        }
    }

    override fun updateTask(id: Int, name: String, type: Int, content: String) {
        val cv = ContentValues()
        cv.put(TaskDatabaseHelper.COLUMN_NAME, name)
        cv.put(TaskDatabaseHelper.COLUMN_TYPE, type)
        cv.put(TaskDatabaseHelper.COLUMN_CONTENT, content)

        db.update(TaskDatabaseHelper.TABLE, cv,
                TaskDatabaseHelper.COLUMN_ID + " =?",
                arrayOf(id.toString())).toLong()
    }

    override fun saveTask(task: Task) {
        if (getTaskById(task.id) != null && task.id != -1)
            updateTask(task.id, task.title, task.type, task.data)
        else addTask(task.title, task.type, task.data)
    }

    override fun deleteTask(id: Long) {
        db.delete(TaskDatabaseHelper.TABLE, TaskDatabaseHelper.COLUMN_ID +
                " =?", arrayOf(id.toString()))
    }

    override fun searchDynamicTask(data: String): MutableList<Task> {
        val cursor = db.rawQuery("SELECT * FROM task_table " +
                "WHERE name LIKE '%$data%' OR content LIKE '%$data%'", null)
        val list = cursorToList(cursor)
        cursor.close()
        return list
    }
}

