package com.example.mytodo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodo.R
import com.example.mytodo.TaskDBViewModel
import com.example.mytodo.data.Task
import com.example.mytodo.databinding.FragmentUpdateBinding

private lateinit var binding: FragmentUpdateBinding

private lateinit var viewModel: TaskDBViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_update,
            container,
            false
        )
        viewModel = ViewModelProvider(requireActivity()).get(TaskDBViewModel::class.java)



        binding.updateTitle.setText(args.currentTask.title)
        binding.updateDescrib.setText(args.currentTask.describtion)

        binding.buttonUpdate.setOnClickListener {
            updateItem()
        }


        return binding.root
    }

    private fun updateItem() {
        val tit = binding.updateTitle.text.toString()
        val desc = binding.updateDescrib.text.toString()

        //check that title is not empty
        if (checkTitle()) {
            val updatedTask = Task(args.currentTask.taskId, tit, desc)
            viewModel.update(updatedTask)
            Toast.makeText(requireContext(), "Task updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill in the title", Toast.LENGTH_SHORT).show()

        }

    }

    //check that title is not empty
    private fun checkTitle(): Boolean {
        if (binding.updateTitle.text.isEmpty()) {
            return false
        }
        return true
    }

}