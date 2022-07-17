package com.example.calendarapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarapp.adapters.AllTasksAdapter
import com.example.calendarapp.R
import com.example.calendarapp.model.DeleteTaskRequestModel
import com.example.calendarapp.model.TasksResponseModel
import com.example.calendarapp.repository.TaskRepository
import com.example.calendarapp.util.Constants
import com.example.calendarapp.util.Resource

class AllTaskActivity : AppCompatActivity() {

    private lateinit var viewModel: AllTasksViewModel
    private lateinit var taskAdapter: AllTasksAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_task)

        val repo = TaskRepository()
        val viewModelFactory = AllTasksViewModelProvider(repo)
        viewModel = ViewModelProvider(this, viewModelFactory)[AllTasksViewModel::class.java]

        supportActionBar?.hide()
        
        initViews()
        observeDataAndInflateRV()
    }

    private fun observeDataAndInflateRV() {
        viewModel.taskList.observe(this) {
            when (it) {
                is Resource.Success -> {
                    taskAdapter = AllTasksAdapter(it.data!!.tasks) { position ->
                        deleteTask(
                            position = position,
                            taskModel = it.data
                        )
                    }
                    rv.adapter = taskAdapter
                    rv.layoutManager = LinearLayoutManager(applicationContext)
                    progressBar.visibility = View.INVISIBLE
                }
                is Resource.Loading -> {
                    showLoadingState()
                }
                else -> {
                    showErrorState()
                }
            }
        }
    }
    
    private fun deleteTask(position: Int,taskModel: TasksResponseModel?) {
        viewModel.deleteTask(
            DeleteTaskRequestModel(
                user_id = Constants.USER_ID,
                task_id = taskModel!!.tasks[position].task_id
            )
        )
        taskAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext,"Deleted",Toast.LENGTH_SHORT).show()
    }
    
    private fun showLoadingState() {
        progressBar.visibility = View.VISIBLE
    }

    private fun showErrorState() {
        Toast.makeText(applicationContext, "Some error occurred", Toast.LENGTH_SHORT).show()
    }
    
    private fun initViews() {
        rv = findViewById(R.id.rv)
        progressBar = findViewById(R.id.progress_bar)
    }
}