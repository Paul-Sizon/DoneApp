package com.example.mytodo


import android.app.Application
import androidx.lifecycle.*
import com.example.mytodo.data.Task
import com.example.mytodo.data.TaskDatabase
import com.example.mytodo.data.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//class TaskViewModel : ViewModel() {
//    var titleOfTask = MutableLiveData<String>()
//    var describOfTask = MutableLiveData<String>()
//    fun setMsgtoViewModel(msg: String, msg2: String) {
//        titleOfTask.value = msg
//        describOfTask.value = msg2
//    }
//}

class TaskDBViewModel(application: Application): AndroidViewModel(application) {
    val getAllTasks: LiveData<List<Task>>
    private val repository: TaskRepository
    init {
        val taskDatabaseDao = TaskDatabase.getDatabase(application).taskDatabaseDao()
        repository = TaskRepository(taskDatabaseDao)
        getAllTasks = repository.getAllTasks
    }

    fun insert(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(task)
        }
    }
}



