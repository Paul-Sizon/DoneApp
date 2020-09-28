package com.example.mytodo

import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.findNavController
import com.example.mytodo.databinding.FragmentNewTaskBinding


class NewTaskFragment : Fragment() {
    private lateinit var binding: FragmentNewTaskBinding

    private lateinit var viewModel: TaskViewModel

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

        viewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)

        //action
        binding.buttonDone.setOnClickListener {
            //set the message to another fragment
            viewModel.setMsgtoViewModel(
                binding.editTextTitle.text.toString(),
                binding.editTextDescrib.text.toString()
            )

            findNavController().navigate(NewTaskFragmentDirections.actionNewTaskFragmentToListFragment())

        }

        return binding.root
    }


}


