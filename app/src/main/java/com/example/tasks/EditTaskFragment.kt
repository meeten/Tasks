package com.example.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.tasks.databinding.FragmentEditTaskBinding

class EditTaskFragment : Fragment() {
    private var _binding: FragmentEditTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var editTaskViewModelFactory: EditTaskViewModelFactory
    private lateinit var editTaskViewModel: EditTaskViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTaskBinding.inflate(inflater)
        val view = binding.root
        val application = requireNotNull(this.activity).application
        val taskDao = TaskDatabase.getInstance(application).taskDao
        val taskId = EditTaskFragmentArgs.fromBundle(requireArguments()).taskId
        editTaskViewModelFactory = EditTaskViewModelFactory(taskId, taskDao)
        editTaskViewModel =
            ViewModelProvider(this, editTaskViewModelFactory)[EditTaskViewModel::class.java]
        binding.editTaskViewModel = editTaskViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        editTaskViewModel.navigatedToList.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                view.findNavController().navigate(R.id.action_editTaskFragment_to_tasksFragment)
                editTaskViewModel.onListNavigated()
            }
        }

        return view
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}