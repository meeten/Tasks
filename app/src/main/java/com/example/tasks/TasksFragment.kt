package com.example.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tasks.databinding.FragmentTasksBinding

class TasksFragment : Fragment() {
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private lateinit var tasksViewModelFactory: TasksViewModelFactory
    private lateinit var tasksViewModel: TasksViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val taskDao = TaskDatabase.getInstance(application).taskDao
        tasksViewModelFactory = TasksViewModelFactory(taskDao)
        tasksViewModel = ViewModelProvider(this, tasksViewModelFactory)[TasksViewModel::class.java]
        binding.tasksViewModel = tasksViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = TaskItemAdapter { taskId ->
            tasksViewModel.onTaskClicked(taskId)
        }
        binding.rcView.adapter = adapter

        tasksViewModel.tasks.observe(viewLifecycleOwner) {
            it?.let { adapter.submitList(it) }
        }

        tasksViewModel.navigateToTask.observe(viewLifecycleOwner) { taskId ->
            taskId?.let {
                val action = TasksFragmentDirections.actionTasksFragmentToEditTaskFragment(taskId)
                this.findNavController().navigate(action)
                tasksViewModel.onTaskNavigated()
            }
        }



        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}