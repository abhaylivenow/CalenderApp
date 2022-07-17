package com.example.calendarapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calendarapp.repository.TaskRepository

class AllTasksViewModelProvider(
    private val repo: TaskRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AllTasksViewModel(repo) as T
    }
}