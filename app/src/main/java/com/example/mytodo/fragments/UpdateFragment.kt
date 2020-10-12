package com.example.mytodo.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodo.R
import com.example.mytodo.viewmodel.TaskDBViewModel
import com.example.mytodo.data.Task
import com.example.mytodo.databinding.FragmentUpdateBinding

private lateinit var binding: FragmentUpdateBinding

private lateinit var viewModel: TaskDBViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_update,
            container,
            false
        )
        viewModel = ViewModelProvider(requireActivity()).get(TaskDBViewModel::class.java)



        binding.updateTitle.setText(args.currentTask.title)
        binding.updateDescrib.setText(args.currentTask.describtion)

        binding.buttonUpdate.setOnClickListener {
            updateItem()
        }

        //menu
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun updateItem() {
        val tit = binding.updateTitle.text.toString()
        val desc = binding.updateDescrib.text.toString()

        if (checkTitle()) {
            val updated = Task(args.currentTask.taskId, tit, desc)
            viewModel.update(updated)
            Toast.makeText(requireContext(), "Task updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), R.string.please_fill, Toast.LENGTH_SHORT).show()

        }

    }

    //check that title is not empty
    private fun checkTitle(): Boolean {
        if (binding.updateTitle.text.isEmpty()) {
            return false
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deleteOneTask()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteOneTask(){
            viewModel.deleteOne(args.currentTask)
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

    }
}