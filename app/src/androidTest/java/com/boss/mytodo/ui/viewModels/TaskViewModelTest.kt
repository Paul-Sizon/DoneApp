package com.boss.mytodo.ui.viewModels

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.boss.mytodo.data.db.TaskDatabase
import com.boss.mytodo.data.db.TaskDatabaseDao
import com.boss.mytodo.data.db.entity.Task
import com.boss.mytodo.data.repositories.TaskRepository
import com.boss.mytodo.getOrAwaitValue
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runner.manipulation.Ordering


@RunWith(AndroidJUnit4::class)
class TaskViewModelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var viewModel: TaskViewModel
    private lateinit var database: TaskDatabase
    private lateinit var dao: TaskDatabaseDao

    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TaskDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.taskDatabaseDao()
        val repository = TaskRepository(dao)

        viewModel = TaskViewModel(repository)
    }

    @After
    fun tearDown(){
        database.close()
    }




    @ExperimentalCoroutinesApi
    @Test
    fun test_TaskViewModel_works(){
        val task1 = Task(324535, "yoman", "yoyoy")
        viewModel.insert(task1)
        val result = viewModel.getAllTasksAsc.getOrAwaitValue().find {
            it.title == "yoman" && it.description =="yoyoy"
        }

        assertThat(result != null).isTrue()

    }


}