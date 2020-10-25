package com.example.mytodo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mytodo.R
import com.example.mytodo.checkLanguage
import com.example.mytodo.checksTitle
import com.example.mytodo.data.Task
import com.example.mytodo.databinding.FragmentNewTaskBinding
import com.example.mytodo.hasNetworkAvailable
import com.example.mytodo.ui.TaskDBViewModel
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.launch


class NewTaskFragment : Fragment() {
    private lateinit var binding: FragmentNewTaskBinding
    private lateinit var viewModel: TaskDBViewModel

    //private val TAG = "MyFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_task,
            container,
            false
        )

        viewModel = ViewModelProvider(requireActivity()).get(TaskDBViewModel::class.java)

        // inspirational phrase
        binding.motivButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            passThePhrase()

        }


        //add new task
        binding.addButton.setOnClickListener {
            insertDataToDatabase()
        }

        return binding.root
    }


    /** database block  */

    private fun insertDataToDatabase() {
        val task = Task(
            0,
            title = binding.editTextTitle.text.toString(),
            describtion = binding.editTextDescrib.text.toString()
        )

        //action
        if (checksTitle(binding.editTextTitle, binding.materialText)) {
            viewModel.insert(task)
            Toast.makeText(requireContext(), R.string.new_added, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_newTaskFragment_to_listFragment)
        }
    }


    /** inspirational phrase block  */

    private fun passThePhrase() {
        if (hasNetworkAvailable()) {
            getInspiration()
        } else {
            binding.motivationText.text = getString(R.string.internet_connect)
            binding.motivationAuthor.text = getString(R.string.confucius)
            binding.progressBar.visibility = View.GONE

        }
    }


    private fun getInspiration() = lifecycleScope.launch {
        viewModel.getPost(checkLanguage())
        viewModel.myResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                binding.motivationText.text = response.body()?.quoteText
                binding.motivationAuthor.text = response.body()?.quoteAuthor
                binding.progressBar.visibility = View.GONE
            } else {
                binding.motivationText.text = response.errorBody().toString()
            }


        })
    }


}


