package com.boss.mytodo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.boss.mytodo.data.db.entity.Task


@Database(
    entities = [Task::class], // Tell the database the entries will hold data of this type
    version = 8
)

abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDatabaseDao(): TaskDatabaseDao
}