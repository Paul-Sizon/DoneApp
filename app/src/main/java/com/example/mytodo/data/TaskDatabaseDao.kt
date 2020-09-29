package com.example.mytodo.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDatabaseDao{
    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM task_table ORDER BY taskId DESC LIMIT 1")
    fun getOneTask(): Task?

    @Query("SELECT * FROM task_table ORDER BY taskId DESC")
    fun getAllTasks(): LiveData<List<Task>>
}