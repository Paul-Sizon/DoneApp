package com.example.mytodo.data

import androidx.lifecycle.LiveData
import com.example.mytodo.data.db.TaskDatabaseDao
import com.example.mytodo.data.db.entity.Task
import com.example.mytodo.network.model.Post
import com.example.mytodo.network.RetroApi
import retrofit2.Response

class TaskRepository(private val taskDao: TaskDatabaseDao) {

    val getAllTasks: LiveData<List<Task>> = taskDao.getAllTasks()


    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    suspend fun deleteOne(task: Task) {
        taskDao.deleteOne(task)
    }

    suspend fun deleteAll() {
        taskDao.deleteAll()
    }


    suspend fun getCount():Int {
        return taskDao.getCount()
    }

    suspend fun getPost(language: String): Response<Post> {
        return RetroApi.api.getPost(language)
    }

}