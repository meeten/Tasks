package com.example.tasks

import androidx.lifecycle.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TasksViewModel(private val dao: TaskDao) : ViewModel() {
    var newTaskName = ""

    private val tasks = dao.getAll()
    val tasksString =  tasks.map { tasks -> formatTasks(tasks) }

    fun addTask() {
        viewModelScope.launch {
            val task = Task()
            task.taskName = newTaskName
            dao.insert(task)
        }
    }

    private fun formatTasks(tasks: List<Task>): String {
        return tasks.fold("") { str, task -> str + "\n" + formatTask(task) }
    }

    private fun formatTask(task: Task): String {
        var str = "ID: ${task.taskId}"
        str += "\n" + "Name: ${task.taskName}"
        str += "\n" + "Complete: ${task.taskDone}" + "\n"
        return str
    }
}