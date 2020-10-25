package com.example.mytodo.ui.fragments

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodo.ui.MyAdapter
import com.example.mytodo.R
import com.example.mytodo.data.Task
import com.example.mytodo.databinding.FragmentListBinding
import com.example.mytodo.hideKeyboard
import com.example.mytodo.ui.TaskDBViewModel
import kotlinx.coroutines.launch
import java.lang.Thread.sleep


open class ListFragment : Fragment(), MyAdapter.TaskEvents {

    private lateinit var viewModel: TaskDBViewModel
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: MyAdapter

    private val args by navArgs<ListFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (::binding.isInitialized) {
            binding.root
        } else {
            binding = FragmentListBinding.inflate(inflater, container, false)

            //viewmodel
            viewModel = ViewModelProvider(this).get(TaskDBViewModel::class.java)


            //recyclerView
            adapter = MyAdapter(this)
            val recyclerview = binding.recyclerView
            recyclerview.adapter = adapter

            viewModel.getAllTasks.observe(viewLifecycleOwner, { adapter.submitList(it) })

            //button action with material design motion
            binding.floatingActionButton.setOnClickListener {
                val extras =
                    FragmentNavigatorExtras(binding.floatingActionButton to "shared_element_container")
                findNavController().navigate(
                    R.id.action_listFragment_to_newTaskFragment, null, null, extras
                )

            }

            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideKeyboard(activity as Activity)

        //menu
        setHasOptionsMenu(true)
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

                Toast.makeText(requireContext(), R.string.all_deleted, Toast.LENGTH_SHORT)
                    .show()

            }
            builder.setNegativeButton(R.string.No) { _, _ -> }
            builder.setTitle(getString(R.string.delete_all))
            builder.setMessage(getString(R.string.are_you_sure))
            builder.create().show()
        }
    }


    override fun onDeleteClicked(task: Task, view: View) {
        viewModel.deleteOne(task)

        /**put it on different coroutine? */
        sleep(300)
        view.visibility = View.GONE

    }

    override fun onViewClicked(task: Task, view: View) {
        val action = ListFragmentDirections.actionListFragmentToUpdateFragment(task)
        val extras =
            FragmentNavigatorExtras(view to ViewCompat.getTransitionName(view)!!)
        findNavController().navigate(action, extras)
    }


}



