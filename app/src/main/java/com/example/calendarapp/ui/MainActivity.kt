package com.example.calendarapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.example.calendarapp.R
import com.example.calendarapp.model.AddTaskRequestModel
import com.example.calendarapp.model.TaskDetail
import com.example.calendarapp.repository.TaskRepository
import com.example.calendarapp.util.Constants
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var btnAllTask: Button
    private lateinit var btnAddTask: Button
    private lateinit var taskDate: TextView
    private lateinit var edtTaskTitle: EditText
    private lateinit var edtTaskDescription: EditText
    private lateinit var taskCard: CardView
    private lateinit var progressBar: ProgressBar
    private lateinit var calendar: SimpleCalendar
    private lateinit var viewModel: MainActivityViewModel
    val calenderInstance: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repo = TaskRepository()
        val viewModelFactory = MainActivityViewModelProvider(repo)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]
        
        supportActionBar?.hide()
        
        initViews()
        setOnClickedListeners()
    }
    
    private fun setOnClickedListeners() {
        calendar.setCallBack(object : SimpleCalendar.DayClickListener {
            override fun onDayClick(view: View?) {
                taskCard.visibility = View.VISIBLE
                edtTaskTitle.setText("")
                edtTaskDescription.setText("")
                val button = view as Button
                val day = button.text.toString()
                val month = (calenderInstance.get(Calendar.MONTH) + 1).toString()
                val year = calenderInstance.get(Calendar.YEAR).toString()
                val date = getDate(day,month,year)
                taskDate.text = date
            }
        })

        btnAddTask.setOnClickListener {
            viewModel.addTask(
                AddTaskRequestModel(
                    user_id = Constants.USER_ID,
                    task = TaskDetail(
                        date = taskDate.text.toString(),
                        description = edtTaskDescription.text.toString(),
                        title = edtTaskTitle.text.toString()
                    )
                )
            )
            Toast.makeText(applicationContext,"Task Posted Successfully",Toast.LENGTH_SHORT).show()
        }

        btnAllTask.setOnClickListener {
            startActivity(
                Intent(this@MainActivity, AllTaskActivity::class.java)
            )
        }
    }

    private fun getDate(
        date: String,
        month: String,
        year: String
    ): String {
        val updatedDay = if (date.length == 1) {
            "0$date"
        } else date

        val updatedMonth = if (month.length == 1) {
            "0$month"
        } else month

        return "$updatedDay-$updatedMonth-$year"
    }

    private fun initViews() {
        calendar = findViewById(R.id.calender)
        btnAllTask = findViewById(R.id.btn_all_task)
        taskDate = findViewById(R.id.task_date)
        edtTaskTitle = findViewById(R.id.edt_task_name)
        edtTaskDescription = findViewById(R.id.edt_task_description)
        taskCard = findViewById(R.id.task_card)
        btnAddTask = findViewById(R.id.btn_add_task)
        progressBar = findViewById(R.id.progress_bar)
    }
}