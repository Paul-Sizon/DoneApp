package com.example.mytodo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task (
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0L,

    @ColumnInfo(name = "title_task")
    var title: String = "",

    @ColumnInfo(name = "describtion_task")
    var describtion: String = ""
)