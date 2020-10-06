package com.example.mytodo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mytodo.network.model.Post
import com.example.mytodo.network.retroApi
import retrofit2.Response
import retrofit2.Retrofit

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

    suspend fun getPost(): Response<Post> {
        return retroApi.api.getPost()
    }

}