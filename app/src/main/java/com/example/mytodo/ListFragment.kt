package com.example.mytodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mytodo.databinding.FragmentListBinding
import com.example.mytodo.databinding.FragmentNewTaskBinding


class ListFragment : Fragment() {
    private lateinit var viewModel: TaskViewModel

    private lateinit var binding: FragmentListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_list,
            container,
            false
        )

        viewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)

        //receive message
        viewModel.titleOfTask.observe(viewLifecycleOwner, { binding.textTask.text = it.toString() })
        viewModel.describOfTask.observe(viewLifecycleOwner, { binding.textDescrib.text = it.toString() })


        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToNewTaskFragment()
            )
        }


        return binding.root
    }


}