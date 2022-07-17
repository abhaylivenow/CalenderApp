package com.example.calendarapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendarapp.model.DeleteTaskRequestModel
import com.example.calendarapp.model.GetTasksRequestBody
import com.example.calendarapp.model.TasksResponseModel
import com.example.calendarapp.repository.TaskRepository
import com.example.calendarapp.util.Constants
import com.example.calendarapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class AllTasksViewModel(
    private val repo: TaskRepository
) : ViewModel(){

    val taskList: MutableLiveData<Resource<TasksResponseModel>> = MutableLiveData()
    var task: TasksResponseModel? = null

    init {
        getTasksList()
    }

    private fun getTasksList() = viewModelScope.launch {
        taskList.postValue(Resource.Loading())
        val response = repo.getTask(GetTasksRequestBody(Constants.USER_ID))
        taskList.postValue(handleResponse(response))
    }

    private fun handleResponse(
        response: Response<TasksResponseModel>
    ): Resource<TasksResponseModel> {
        if(response.isSuccessful) {
            response.body()?.let {
                task = it
                return Resource.Success(task ?: it)
            }
        }
        return Resource.Error(response.message())
    }

    fun deleteTask(deleteTaskRequestModel: DeleteTaskRequestModel) {
        viewModelScope.launch {
            repo.deleteTask(deleteTaskRequestModel)
        }
    }
}