package com.boss.mytodo.ui


import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.boss.mytodo.data.db.entity.Task
import com.boss.mytodo.data.db.TaskDatabase
import com.boss.mytodo.data.TaskRepository
import com.boss.mytodo.network.model.Motivation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class TaskDBViewModel(application: Application) : AndroidViewModel(application) {

    val getAllTasks: LiveData<List<Task>>

    private val repository: TaskRepository
    val motivationLive: MutableLiveData<Motivation> = MutableLiveData()

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

    fun getMotivation(language: String) = viewModelScope.launch(Dispatchers.Default) {
        try {
            val response = repository.getMotivation(language)
            motivationLive.postValue(response.body())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    Log.e("TaskViewModel", throwable.message.toString())
                    motivationLive.postValue(null)
                }
            }
        }
    }
}



