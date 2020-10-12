package com.example.mytodo.fragments

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodo.MyAdapter
import com.example.mytodo.R
import com.example.mytodo.data.Task
import com.example.mytodo.databinding.FragmentListBinding
import com.example.mytodo.hideKeyboard
import com.example.mytodo.viewmodel.TaskDBViewModel
import kotlinx.coroutines.launch
import java.util.*


open class ListFragment : Fragment(), MyAdapter.TaskEvents {

    private lateinit var viewModel: TaskDBViewModel
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: MyAdapter

    private val args by navArgs<ListFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)

        hideKeyboard(activity as Activity)

        //viewmodel
        viewModel = ViewModelProvider(this).get(TaskDBViewModel::class.java)

        //recyclerView
        adapter = MyAdapter(this)
        val recyclerview = binding.recyclerView
        recyclerview.adapter = adapter

        viewModel.getAllTasks.observe(viewLifecycleOwner, { adapter.submitList(it) })


        Log.i("lannguage", Locale.getDefault().language)
        fun checkLanguage() {
            if (Locale.getDefault().language == "en") {
                Log.i("lannguage", "it's true")
            }
        }
        checkLanguage()


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


    private fun deleteAll() = lifecycleScope.launch {

        val num = viewModel.getCount()
        if (num != 0) {

            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton(R.string.Yes) { _, _ ->
                viewModel.deleteAll()
                Toast.makeText(requireContext(), R.string.all_deleted, Toast.LENGTH_SHORT).show()

            }
            builder.setNegativeButton(R.string.No) { _, _ -> }
            builder.setTitle(getString(R.string.delete_all))
            builder.setMessage(getString(R.string.are_you_sure))
            builder.create().show()
        }
    }

    override fun onDeleteClicked(task: Task) {
        viewModel.deleteOne(task)
    }


}



