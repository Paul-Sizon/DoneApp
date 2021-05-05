package com.boss.mytodo.data

import androidx.lifecycle.LiveData
import com.boss.mytodo.data.db.TaskDatabaseDao
import com.boss.mytodo.data.db.entity.Task
import com.boss.mytodo.network.QuoteApi
import com.boss.mytodo.network.model.Motivation
import retrofit2.Response
import javax.inject.Inject

class TaskRepository@Inject constructor(private val api: QuoteApi, private val taskDao: TaskDatabaseDao) {

    val getAllTasksDesc: LiveData<List<Task>> = taskDao.getAllTasksDesc()
    val getAllTasksAsc: LiveData<List<Task>> = taskDao.getAllTasksAsc()


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

    suspend fun getMotivation(language: String): Response<Motivation> {
        return api.getMotivation(language)
    }

}