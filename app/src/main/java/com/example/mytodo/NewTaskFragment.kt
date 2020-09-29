package com.example.mytodo

import android.os.Bundle
import android.text.TextUtils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.findNavController
import com.example.mytodo.data.Task
import com.example.mytodo.databinding.FragmentNewTaskBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_new_task.*


class NewTaskFragment : Fragment() {
    private lateinit var binding: FragmentNewTaskBinding

    private lateinit var viewModel: TaskDBViewModel

    //private val TAG = "MyFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_task,
            container,
            false
        )

       viewModel = ViewModelProvider(requireActivity()).get(TaskDBViewModel::class.java)

        //action
        binding.buttonDone.setOnClickListener {
            //send the message to another fragment
//            viewModel.setMsgtoViewModel(
//                binding.editTextTitle.text.toString(),
//                binding.editTextDescrib.text.toString()
//            )

            insertDataToDatabase()

        }

        return binding.root
    }
    private fun insertDataToDatabase() {
        val title = binding.editTextTitle.text.toString()
        val describtion = binding.editTextDescrib.text.toString()
        val task = Task(0, title, describtion)

        //check that title is not empty
        if (checkTitle(title)){
            //action
            viewModel.insert(task)
            Toast.makeText(requireContext(), "New Task added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(NewTaskFragmentDirections.actionNewTaskFragmentToListFragment())
        }else{ Snackbar.make(fragment_new, "Please add the title", Snackbar.LENGTH_SHORT).show()

        }

    }
    //check that title is not empty
    private fun checkTitle(title: String): Boolean{
        return !(TextUtils.isEmpty(title))
    }

}


