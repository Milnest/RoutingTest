package com.milnest.testapp.tasklist.data.repository

import com.milnest.testapp.tasklist.entities.Task

class AppDataRepository : IDataRepository {
    private lateinit var data: IDataRepository
    override fun setIData(isDemo: Boolean) {
        data = if (isDemo) DemoRepository() else DBRepository()
    }

    override fun getIData(): IDataRepository {
        return data
    }

    override fun getAllTasks(): MutableList<Task> {
        return data.getAllTasks()
    }

    override fun addTask(name: String, type: Int, content: String) {
        data.addTask(name, type, content)
    }

    override fun getTaskById(id: Int): Task? {
        return data.getTaskById(id)
    }

    override fun updateTask(id: Int, name: String, type: Int, content: String) {
        data.updateTask(id, name, type, content)
    }

    override fun saveTask(task: Task) {
        data.saveTask(task)
    }

    override fun deleteTask(id: Long) {
        data.deleteTask(id)
    }

    override fun searchDynamicTask(data: String): MutableList<Task> {
        return this.data.searchDynamicTask(data)
    }
}