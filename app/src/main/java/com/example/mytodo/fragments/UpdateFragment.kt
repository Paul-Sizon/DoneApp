package com.example.mytodo.fragments


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodo.R
import com.example.mytodo.data.db.entity.Task
import com.example.mytodo.databinding.FragmentUpdateBinding
import com.example.mytodo.viewmodel.TaskDBViewModel
import com.google.android.material.transition.MaterialContainerTransform

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding

    private val viewModel: TaskDBViewModel by viewModels()

    private val args by navArgs<UpdateFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
        sharedElementReturnTransition = MaterialContainerTransform()

        
    }



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

        // gives unique name to the updateFragment in order to support Material Transition for individual item in recycler view
        ViewCompat.setTransitionName(binding.framelayoutUpdate, args.currentTask.taskId.toString())

        // sets current text
        binding.updateTitle.setText(args.currentTask.title)
        binding.updateDescrib.setText(args.currentTask.describtion)

        // action
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
        if (binding.updateTitle.text?.isEmpty()!!) {
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