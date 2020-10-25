package com.example.mytodo.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "task_table")
data class Task (
    @PrimaryKey(autoGenerate = true)
    var taskId: Int = 0,

    @ColumnInfo(name = "title_task")
    var title: String = "",

    @ColumnInfo(name = "describtion_task")
    var description: String = ""
): Parcelable