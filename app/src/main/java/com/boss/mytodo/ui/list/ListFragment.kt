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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG
import androidx.recyclerview.widget.RecyclerView
import com.boss.mytodo.R
import com.boss.mytodo.data.SharedPrefs
import com.boss.mytodo.data.db.entity.Task
import com.boss.mytodo.databinding.FragmentListBinding
import com.boss.mytodo.other.Utils
import com.boss.mytodo.ui.MyAdapter
import com.boss.mytodo.ui.viewModels.TaskViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


open class ListFragment : Fragment(), MyAdapter.TaskEvents {

    private val viewModel: TaskViewModel by activityViewModels()
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: MyAdapter
    private lateinit var recyclerview: RecyclerView
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
        recyclerview = binding.recyclerView
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

        class SimpleItemTouchHelperCallback: ItemTouchHelper.Callback() {

            override fun isLongPressDragEnabled(): Boolean {
                return true
            }

            override fun isItemViewSwipeEnabled(): Boolean {
                return true
            }
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                // Specify the directions of movement
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }
            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (actionState == ACTION_STATE_DRAG) {
                    viewHolder?.itemView?.performHapticFeedback(
                        HapticFeedbackConstants.KEYBOARD_TAP,
                        HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
                    )
                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteOne(adapter.getItem(viewHolder.adapterPosition))
                restoreData(viewHolder.itemView, adapter.getItem(viewHolder.adapterPosition))
            }
        }

        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback()
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerview)


    }

    private fun restoreData(
        view: View,
        task: Task
    ) {
        Snackbar.make(view, "${getString(R.string.Delete)} ${task.title}", Snackbar.LENGTH_LONG).also {
            it.apply {
                setAction(getString(R.string.Undo)) {
                    viewModel.insert(task)
                }
                show()
            }
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