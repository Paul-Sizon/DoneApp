package com.example.mytodo.ui.list

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodo.MyAdapter
import com.example.mytodo.R
import com.example.mytodo.Utils
import com.example.mytodo.data.db.entity.Task
import com.example.mytodo.databinding.FragmentListBinding
import com.example.mytodo.fragments.ListFragmentArgs
import com.example.mytodo.fragments.ListFragmentDirections
import com.example.mytodo.viewmodel.TaskDBViewModel
import kotlinx.coroutines.launch


open class ListFragment : Fragment(), MyAdapter.TaskEvents {

    private val viewModel: TaskDBViewModel by activityViewModels()
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: MyAdapter

    private val args by navArgs<ListFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postponeEnterTransition()
        binding = FragmentListBinding.inflate(inflater, container, false)


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
        binding.root.doOnPreDraw {
            startPostponedEnterTransition()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Utils.hideKeyboard(activity as Activity)

        //menu
        setHasOptionsMenu(true)
    }

    private fun subscribeObservers() {

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
    }

    override fun onViewClicked(task: Task, view: View) {
        val action = ListFragmentDirections.actionListFragmentToUpdateFragment(task)
        val extras =
            FragmentNavigatorExtras(view to ViewCompat.getTransitionName(view)!!)
        findNavController().navigate(action, extras)
    }
}