package com.example.calendarapp.repository

import com.example.calendarapp.api.RetrofitBuilder
import com.example.calendarapp.model.AddTaskRequestModel
import com.example.calendarapp.model.DeleteTaskRequestModel
import com.example.calendarapp.model.RequestBody

class TaskRepository {
    suspend fun getTask(requestBody: RequestBody) = RetrofitBuilder.api.getTaskList(requestBody)

    suspend fun addTask(addTaskRequestModel: AddTaskRequestModel) =
        RetrofitBuilder.api.addTask(addTaskRequestModel)

    suspend fun deleteTask(deleteTaskRequestModel: DeleteTaskRequestModel) =
        RetrofitBuilder.api.deleteTask(deleteTaskRequestModel)
}