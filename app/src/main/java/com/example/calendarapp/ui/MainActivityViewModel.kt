package com.example.calendarapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendarapp.model.AddTaskRequestModel
import com.example.calendarapp.repository.TaskRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val repo: TaskRepository
): ViewModel() {

    fun addTask(addTaskRequestModel: AddTaskRequestModel) {
        viewModelScope.launch {
            repo.addTask(addTaskRequestModel)
        }
    }
}