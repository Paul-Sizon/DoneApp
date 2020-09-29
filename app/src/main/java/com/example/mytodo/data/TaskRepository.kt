package com.example.mytodo.data

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDatabaseDao) {

    val getAllTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    suspend fun insert(task: Task){
        taskDao.insert(task)
    }
}