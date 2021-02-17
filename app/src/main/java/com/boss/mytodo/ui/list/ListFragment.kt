package com.boss.mytodo.ui.list

import android.app.Activity
import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.boss.mytodo.R
import com.boss.mytodo.Utils
import com.boss.mytodo.data.SharedPrefs
import com.boss.mytodo.data.db.entity.Task
import com.boss.mytodo.databinding.FragmentListBinding
import com.boss.mytodo.ui.MyAdapter
import com.boss.mytodo.ui.TaskDBViewModel
import kotlinx.coroutines.launch


open class ListFragment : Fragment(), MyAdapter.TaskEvents {

    private val viewModel: TaskDBViewModel by activityViewModels()
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: MyAdapter
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postponeEnterTransition()
        binding = FragmentListBinding.inflate(inflater, container, false)
        editor = SharedPrefs(requireContext()).editor
        sharedPrefs = SharedPrefs(requireContext()).sharedPref

        //recyclerView
        adapter = MyAdapter(this)
        val recyclerview = binding.recyclerView
        recyclerview.adapter = adapter


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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUi()
        subscribeObservers()
    }

    private fun subscribeUi() {
        binding.floatingActionButton.setOnClickListener {
            val extras =
                FragmentNavigatorExtras(binding.floatingActionButton to "shared_element_container")
            findNavController().navigate(
                R.id.action_listFragment_to_newTaskFragment, null, null, extras
            )
        }
    }

    private fun subscribeObservers() {
        var checkOrder = sharedPrefs.getBoolean("sort_desc", true)
        if (checkOrder) {
            viewModel.getAllTasksDesc.observe(viewLifecycleOwner, { adapter.submitList(it) })
        } else {
            viewModel.getAllTasksAsc.observe(viewLifecycleOwner, { adapter.submitList(it) })
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.delete) {
            deleteAll()
        }
        if (item.itemId == R.id.sort) {
            var checkOrder = sharedPrefs.getBoolean("sort_desc", true)
            if (checkOrder) {
                viewModel.getAllTasksAsc.observe(viewLifecycleOwner, { adapter.submitList(it) })
                editor.putBoolean("sort_desc", false).apply()
            } else {
                viewModel.getAllTasksDesc.observe(viewLifecycleOwner, { adapter.submitList(it) })
                editor.putBoolean("sort_desc", true).apply()
            }

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
        val navController = findNavController()
        val action = ListFragmentDirections.actionListFragmentToNewTaskFragment(task)
        val transitionName = ViewCompat.getTransitionName(view) ?: return
        val extras = FragmentNavigatorExtras(view to transitionName)

        if (navController.currentDestination?.id == R.id.listFragment) {
            navController.navigate(action, extras)
        }
    }


}