package com.example.mytodo.fragments

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mytodo.MyAdapter
import com.example.mytodo.R
import com.example.mytodo.TaskDBViewModel
import com.example.mytodo.databinding.FragmentListBinding
import com.example.mytodo.hideKeyboard
import kotlinx.coroutines.launch


open class ListFragment : Fragment() {
    
    private lateinit var viewModel: TaskDBViewModel
    private lateinit var binding: FragmentListBinding

    private val TAG = "MyFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater,container,false)
        hideKeyboard(activity as Activity)


        //recyclerView
        val adapter = MyAdapter()
        val recyclerview = binding.recyclerView
        recyclerview.adapter = adapter



        //viewmodel
        viewModel = ViewModelProvider(this).get(TaskDBViewModel::class.java)
        viewModel.getAllTasks.observe(viewLifecycleOwner, { adapter.submitList(it) })



        //button action
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToNewTaskFragment()
            )
        }


        //menu
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteAll()
        }
        return super.onOptionsItemSelected(item)
    }



    private fun deleteAll()= lifecycleScope.launch  {

        val num = viewModel.getCount()
        if (num != 0) {

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteAll()
            Toast.makeText(requireContext(), "All tasks deleted", Toast.LENGTH_SHORT).show()

        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete all tasks?")
        builder.setMessage("Are you sure you want to delete all tasks?")
        builder.create().show()
        }
    }


}

