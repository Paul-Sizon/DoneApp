package com.example.mytodo.ui.task

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.mytodo.R
import com.example.mytodo.data.db.entity.Task
import com.example.mytodo.databinding.FragmentTaskBinding
import com.example.mytodo.viewmodel.TaskDBViewModel
import com.google.android.material.transition.MaterialContainerTransform
import java.util.*


class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private val viewModel: TaskDBViewModel by activityViewModels()

    private val args: TaskFragmentArgs by navArgs()

    //private val TAG = "MyFragment"
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
                binding.motivationAuthor.text = task.quoteAuthor
                binding.pbProgress.visibility = View.GONE
            } else {
                binding.tvMotivation.text =
                    getString(R.string.task_msg_please_connect_to_internet)
                binding.motivationAuthor.text = getString(R.string.task_msg_confucius)
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
            Toast.makeText(requireContext(), R.string.please_fill, Toast.LENGTH_SHORT).show()
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
            Toast.makeText(requireContext(), R.string.please_fill, Toast.LENGTH_SHORT).show()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (args.task != null) inflater.inflate(R.menu.delete, menu)
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