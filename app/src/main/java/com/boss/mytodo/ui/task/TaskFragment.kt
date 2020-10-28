package com.boss.mytodo.ui.task

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.boss.mytodo.R
import com.boss.mytodo.data.db.entity.Task
import com.boss.mytodo.databinding.FragmentTaskBinding
import com.boss.mytodo.ui.TaskDBViewModel
import com.google.android.material.transition.MaterialContainerTransform
import java.util.*


class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private val viewModel: TaskDBViewModel by activityViewModels()

    private val args: TaskFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
        sharedElementReturnTransition = MaterialContainerTransform()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        if (args.task != null) {
            ViewCompat.setTransitionName(binding.ltRoot, args.task?.taskId.toString())
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUi()
        subscribeObservers()
    }

    private fun subscribeUi() {

        // inspirational phrase
        val task = args.task
        if (task == null) {
            binding.apply {
                btnUpdate.text = requireContext().getText(R.string.done_button)
                btnMotivation.setOnClickListener {
                    binding.pbProgress.visibility = View.VISIBLE
                    getMotivation()
                }
                btnUpdate.setOnClickListener {
                    insertDataToDatabase()
                }
            }
        } else {
            binding.apply {
                (requireActivity() as AppCompatActivity).supportActionBar!!.title = getString(R.string.edit)
                btnUpdate.text = requireContext().getText(R.string.update)
                btnUpdate.setOnClickListener {
                    updateItem()
                }
                btnMotivation.visibility = View.GONE
                etTitle.setText(task.title)
                etDiscription.setText(task.description)
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.motivationLive.observe(viewLifecycleOwner, { task ->
            if (task != null) {
                binding.tvMotivation.text = task.quoteText
                binding.tvMotivationAuthor.text = task.quoteAuthor
                binding.pbProgress.visibility = View.GONE
            } else {
                binding.tvMotivation.text =
                    getString(R.string.task_msg_please_connect_to_internet)
                binding.tvMotivationAuthor.text = getString(R.string.task_msg_confucius)
                binding.pbProgress.visibility = View.GONE
            }
        })
    }

    /** inspirational phrase block  */

    // changes phrase based on the system's language. Only russian and english are available on the server
    private fun checkLanguage(): String {
        return if (Locale.getDefault().language == "ru") "ru"
        else "en"
    }

    private fun getMotivation() {
        viewModel.getMotivation(checkLanguage())
    }

    /** database block  */
    private fun insertDataToDatabase() {
        val task = Task(
            0,
            title = binding.etTitle.text.toString(),
            description = binding.etDiscription.text.toString()
        )

        //action
        if (checkTitle()) {
            viewModel.insert(task)
            Toast.makeText(requireContext(), R.string.new_added, Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressed()
        } else {
            binding.materialText.error = getString(R.string.error_empty_title)
        }
    }

    //check that title is not empty
    private fun checkTitle(): Boolean {
        if (binding.etTitle.text?.isEmpty()!!) {
            return false
        }
        return true
    }

    private fun updateItem() {
        val tit = binding.etTitle.text.toString()
        val desc = binding.etDiscription.text.toString()

        if (checkTitle()) {
            val task = args.task ?: return
            val updated = Task(task.taskId, tit, desc)
            viewModel.update(updated)

            Toast.makeText(requireContext(), "Task updated", Toast.LENGTH_SHORT).show()

            requireActivity().onBackPressed()
        } else {
            binding.materialText.error = getString(R.string.error_empty_title)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (args.task != null) {
            inflater.inflate(R.menu.delete, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (args.task != null) {
            if (item.itemId == R.id.menu_delete) {
                deleteOneTask()
            }
            super.onOptionsItemSelected(item)
        } else {
            false
        }
    }


    private fun deleteOneTask() {
        args.task?.let { viewModel.deleteOne(it) }
        requireActivity().onBackPressed()
    }
}