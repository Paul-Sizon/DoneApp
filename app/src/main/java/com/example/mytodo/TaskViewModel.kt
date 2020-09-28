package com.example.mytodo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class TaskViewModel : ViewModel() {
    var titleOfTask = MutableLiveData<String>()
    var describOfTask = MutableLiveData<String>()
    fun setMsgtoViewModel(msg: String, msg2: String) {
        titleOfTask.value = msg
        describOfTask.value = msg2
    }
}



