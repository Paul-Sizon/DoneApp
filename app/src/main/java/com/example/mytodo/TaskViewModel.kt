package com.example.mytodo


import android.app.Application
import androidx.lifecycle.*
import com.example.mytodo.data.Task
import com.example.mytodo.data.TaskDatabase
import com.example.mytodo.data.TaskDatabaseDao
import com.example.mytodo.data.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TaskDBViewModel(application: Application) : AndroidViewModel(application) {
    val getAllTasks: LiveData<List<Task>>
    private val repository: TaskRepository

    init {
        val taskDatabaseDao = TaskDatabase.getDatabase(application).taskDatabaseDao()
        repository = TaskRepository(taskDatabaseDao)
        getAllTasks = repository.getAllTasks
    }

    fun insert(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(task)
        }
    }

    fun update(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(task)
        }
    }

    fun deleteOne(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteOne(task)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    suspend fun getCount(): Int = withContext(Dispatchers.IO) {
        repository.getCount()
    }

   
}



