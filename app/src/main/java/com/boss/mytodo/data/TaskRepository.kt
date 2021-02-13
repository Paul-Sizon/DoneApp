package com.boss.mytodo.data

import androidx.lifecycle.LiveData
import com.boss.mytodo.data.db.TaskDatabaseDao
import com.boss.mytodo.data.db.entity.Task
import com.boss.mytodo.network.model.Motivation
import com.boss.mytodo.network.RetroApi
import retrofit2.Response

class TaskRepository(private val taskDao: TaskDatabaseDao) {

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
        return RetroApi.api.getMotivation(language)
    }

}