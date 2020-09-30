package com.example.mytodo

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mytodo.databinding.FragmentListBinding


class ListFragment : Fragment() {
    private lateinit var viewModel: TaskDBViewModel
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
        hideKeyboard(activity as Activity)


        //recyclerView
        val adapter = MyAdapter()
        val recyclerview = binding.recyclerView
        recyclerview.adapter = adapter


        //viewmodel
        viewModel = ViewModelProvider(this).get(TaskDBViewModel::class.java)
        //viewModel.getAllTasks.observe(viewLifecycleOwner, {it?.let {MyAdapter().myDataList = it}})
        viewModel.getAllTasks.observe(viewLifecycleOwner, {adapter.setData(it)})

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.


        //button action
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToNewTaskFragment()
            )
        }

        return binding.root
    }




}