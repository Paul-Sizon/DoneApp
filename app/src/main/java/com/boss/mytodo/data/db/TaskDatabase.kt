package com.boss.mytodo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.boss.mytodo.data.db.entity.Task

//@Database(entities = [Task::class], version = 7, exportSchema = false)
//abstract class TaskDatabase : RoomDatabase() {
//    abstract fun taskDatabaseDao(): TaskDatabaseDao
//
//    companion object {
//
//        @Volatile
//        private var INSTANCE: TaskDatabase? = null
//
//        fun getDatabase(context: Context): TaskDatabase {
//            synchronized(this) {
//                var instance = INSTANCE
//
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        TaskDatabase::class.java,
//                        "task_database"
//                    )
//                        .fallbackToDestructiveMigration()
//                        .build()
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//    }
//}


@Database(
    entities = [Task::class], // Tell the database the entries will hold data of this type
    version = 8
)

abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDatabaseDao(): TaskDatabaseDao
}