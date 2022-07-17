package com.example.calendarapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarapp.R
import com.example.calendarapp.model.Task

class AllTasksAdapter(
    private val listOfTasks: List<Task>,
    val deleteTask:(index: Int) -> Unit
) : RecyclerView.Adapter<AllTasksAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskDate = itemView.findViewById<TextView>(R.id.item_task_date)
        val taskTitle = itemView.findViewById<TextView>(R.id.item_task_title)
        val taskDescription = itemView.findViewById<TextView>(R.id.item_task_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemiew =
            LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        return ViewHolder(itemiew)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = listOfTasks[position]

        holder.taskDate.text = task.task_detail.date
        holder.taskTitle.text = task.task_detail.title
        holder.taskDescription.text = task.task_detail.description

        holder.itemView.setOnClickListener {
            deleteTask(position)
        }
    }

    override fun getItemCount(): Int {
        return listOfTasks.size
    }
}