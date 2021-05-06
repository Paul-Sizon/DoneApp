package com.boss.mytodo.ui.viewModels


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boss.mytodo.data.repositories.MotivRepository
import com.boss.mytodo.network.model.Motivation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MotivationViewModel @Inject constructor(private val repository: MotivRepository) : ViewModel() {
    val motivationLive: MutableLiveData<Motivation> = MutableLiveData()

    fun getMotivation(language: String) = viewModelScope.launch(Dispatchers.Default) {
        try {
            val response = repository.getMotivation(language)
            motivationLive.postValue(response.body())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    Log.e("MotivViewModel", throwable.message.toString())
                    motivationLive.postValue(null)
                }
            }
        }
    }
}