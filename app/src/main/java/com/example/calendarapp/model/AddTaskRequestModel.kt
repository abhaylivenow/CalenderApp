package com.example.calendarapp.model

data class AddTaskRequestModel(
    val user_id: Int,
    val task: TaskDetail
)