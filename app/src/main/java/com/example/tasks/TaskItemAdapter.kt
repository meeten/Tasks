package com.example.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TaskItemAdapter :
    ListAdapter<Task, TaskItemAdapter.TaskItemViewHolder>(TaskDiffItemCallback()) {
    class TaskItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Task) {
            val taskName = view.findViewById<TextView>(R.id.task_name)
            val taskDone = view.findViewById<CheckBox>(R.id.task_done)

            taskName.text = item.taskName
            taskDone.isClickable = item.taskDone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}