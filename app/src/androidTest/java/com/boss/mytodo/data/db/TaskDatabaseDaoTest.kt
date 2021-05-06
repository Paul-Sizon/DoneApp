package com.boss.mytodo.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.boss.mytodo.data.db.entity.Task
import com.boss.mytodo.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class TaskDatabaseDaoTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var database: TaskDatabase
    private lateinit var dao: TaskDatabaseDao

    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TaskDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.taskDatabaseDao()
    }

    @After
    fun tearDown(){
        database.close()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun insert() = runBlockingTest {
        val task = Task(234234, "heyhey", "yoyoy")
        dao.insert(task)

        val allTasksAsc = dao.getAllTasksAsc().getOrAwaitValue()

        assertThat(allTasksAsc).contains(task)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun deleteOne() = runBlockingTest {
        val task = Task(234234, "heyhey", "yoyoy")
        dao.insert(task)
        dao.deleteOne(task)

        val allTasksAsc = dao.getAllTasksAsc().getOrAwaitValue()

        assertThat(allTasksAsc).doesNotContain(task)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getAll() = runBlockingTest {
        val task1 = Task(324535, "heyheydsfs", "yoyoy")
        val task2 = Task(38476, "fds", "yoyoy")
        val task3 = Task(59595, "tt", "yoyoy")
        dao.insert(task1)
        dao.insert(task2)
        dao.insert(task3)


        val allTasksAsc = dao.getAllTasksAsc().getOrAwaitValue()

        assertThat(allTasksAsc).hasSize(3)
    }
}