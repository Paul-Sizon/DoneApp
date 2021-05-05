package com.boss.mytodo.ui


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boss.mytodo.data.TaskRepository
import com.boss.mytodo.data.db.entity.Task
import com.boss.mytodo.network.model.Motivation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class TaskDBViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {

    val getAllTasksDesc: LiveData<List<Task>> = repository.getAllTasksDesc
    val getAllTasksAsc: LiveData<List<Task>> = repository.getAllTasksAsc
    val motivationLive: MutableLiveData<Motivation> = MutableLiveData()


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



