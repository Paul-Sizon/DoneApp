package com.boss.mytodo.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.boss.mytodo.data.db.entity.Task

@Dao
interface TaskDatabaseDao {
    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun deleteOne(task: Task)
    
    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM task_table ORDER BY taskId DESC LIMIT 1")
    suspend fun getOneTask(): Task?

    @Query("SELECT * FROM task_table ORDER BY taskId DESC")
    fun getAllTasksDesc(): LiveData<List<Task>>

     @Query("SELECT * FROM task_table ORDER BY taskId ASC")
    fun getAllTasksAsc(): LiveData<List<Task>>


    @Query("SELECT COUNT(*) FROM task_table")
    suspend fun getCount(): Int



    }
