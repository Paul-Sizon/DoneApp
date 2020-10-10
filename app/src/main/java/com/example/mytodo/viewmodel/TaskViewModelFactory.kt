package com.example.mytodo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytodo.data.TaskRepository

//class TaskViewModelFactory(
//    private val repository: TaskRepository
//): ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return TaskDBViewModel(repository) as T
//    }
//}