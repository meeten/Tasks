package com.example.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.DEBUG_PROPERTY_VALUE_ON
import kotlinx.coroutines.launch

class EditTaskViewModel(private val taskId: Long, private val taskDao: TaskDao) : ViewModel() {
    val task = taskDao.get(taskId)
    private val _navigateToList = MutableLiveData<Boolean>()
    val navigatedToList: LiveData<Boolean> get() = _navigateToList

    fun updateTask() {
        viewModelScope.launch {
            taskDao.update(task.value!!)
            _navigateToList.value = true
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            taskDao.delete(task.value!!)
            _navigateToList.value = true
        }
    }

    fun onListNavigated() {
        _navigateToList.value = false
    }
}