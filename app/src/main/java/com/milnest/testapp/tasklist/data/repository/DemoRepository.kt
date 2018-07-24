package com.milnest.testapp.tasklist.data.repository

import com.milnest.testapp.tasklist.entities.Task

class DemoRepository : IDataRepository {
    override fun setIData(isDemo: Boolean) = Unit

    override fun getIData(): IDataRepository = this

    val taskList: MutableList<Task> = ArrayList()

    init {

    }

    override fun getAllTasks(): MutableList<Task> {
        return taskList
    }

    override fun addTask(name: String, type: Int, content: String) {
        taskList.add(Task(taskList.size - 1, name, type, content))
    }

    override fun getTaskById(id: Int): Task? {
        for (item in taskList){
            if (item.id == id) {
                return item
            }
        }
        return null
    }

    override fun updateTask(id: Int, name: String, type: Int, content: String) {
        /*for (item in taskList){
            if (item.id == id){
                item.title = name
                item.type = type
                item.data = content
            }
        }*/
        val task = getTaskById(id)
        if (task!= null) {
            task.title = name
            task.type = type
            task.data = content
        }
    }

    override fun saveTask(task: Task) {
        if (getTaskById(task.id) != null && task.id != -1)
            updateTask(task.id, task.title, task.type, task.data)
        else addTask(task.title, task.type, task.data)
    }

    override fun deleteTask(id: Long) {
        val task = getTaskById(id.toInt())
        if (task != null){
            taskList.remove(task)
        }
    }

    override fun searchDynamicTask(data: String): MutableList<Task> {
        val searchList: MutableList<Task> = ArrayList()
        for (item in taskList){
            if (item.title.indexOf(data) != -1 || item.data.indexOf(data) != -1) searchList.add(item)
        }
        return searchList
    }
}