package com.milnest.testapp.tasklist.data.repository

import com.milnest.testapp.tasklist.entities.Task

interface IDataRepository {

    fun getAllTasks(): MutableList<Task>

    fun addTask(name: String, type: Int, content: String)

    fun getTaskById(id: Int): Task?

    fun updateTask(id: Int, name: String, type: Int, content: String)

    fun saveTask(task: Task)

    fun deleteTask(id: Long)

    fun searchDynamicTask(data: String): MutableList<Task>
}