package com.example.mytodo.fragments

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
import com.example.mytodo.viewmodel.TaskDBViewModel
import com.example.mytodo.data.Task
import com.example.mytodo.databinding.FragmentNewTaskBinding
import kotlinx.coroutines.launch
import java.util.*


class NewTaskFragment : Fragment() {
    private lateinit var binding: FragmentNewTaskBinding

    private lateinit var viewModel: TaskDBViewModel

    //private val TAG = "MyFragment"

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
        binding.motivButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            insertMotivation()
        }






        //action
        binding.bbb.setOnClickListener {
            insertDataToDatabase()
        }
        return binding.root
    }




    private fun checkLanguage(): String {
        return if (Locale.getDefault().language == "ru") "ru"
        else "en"
    }


    private fun insertMotivation() = lifecycleScope.launch {
        viewModel.getPost(checkLanguage())
        viewModel.myResponse.observe(viewLifecycleOwner, { response ->


            binding.motivationText.text = response.body()?.quoteText
            binding.motivationAuthor.text = response.body()?.quoteAuthor
            binding.progressBar.visibility = View.GONE


        })
    }


    private fun insertDataToDatabase() {
        val task = Task(
            0,
            title = binding.editTextTitle.text.toString(),
            describtion = binding.editTextDescrib.text.toString()
        )

        //check that title is not empty
        if (checkTitle()) {
            //action
            viewModel.insert(task)
            Toast.makeText(requireContext(), R.string.new_added, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_newTaskFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), R.string.please_fill, Toast.LENGTH_SHORT).show()

        }

    }

    //check that title is not empty
    private fun checkTitle(): Boolean {
        if (binding.editTextTitle.text.isEmpty()) {
            return false
        }
        return true
    }

}


