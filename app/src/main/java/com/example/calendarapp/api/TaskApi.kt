package com.example.calendarapp.api

import com.example.calendarapp.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskApi {
    @POST("/api/getCalendarTaskLists")
    suspend fun getTaskList(@Body getTasksRequestBody: GetTasksRequestBody): Response<TasksResponseModel>

    @POST("/api/storeCalendarTask")
    suspend fun addTask(@Body addTaskRequestModel: AddTaskRequestModel)

    @POST("/api/deleteCalendarTask")
    suspend fun deleteTask(@Body deleteTaskRequestModel: DeleteTaskRequestModel)
}