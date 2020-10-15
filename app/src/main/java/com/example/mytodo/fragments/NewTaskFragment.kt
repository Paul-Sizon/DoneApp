package com.example.mytodo.fragments

import android.os.Bundle
import android.os.SystemClock.sleep
import android.transition.Fade
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
import com.example.mytodo.data.Task
import com.example.mytodo.databinding.FragmentNewTaskBinding
import com.example.mytodo.hasNetworkAvailable
import com.example.mytodo.viewmodel.TaskDBViewModel
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFadeThrough
import kotlinx.coroutines.launch
import java.util.*


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
            checkInternet()
        }


        //add new task
        binding.bbb.setOnClickListener {
            insertDataToDatabase()
        }

        return binding.root
    }


    /** inspirational phrase block  */

    fun checkInternet() {
        if (hasNetworkAvailable()) {
            insertMotivation()
        } else {
            binding.motivationText.text = "Please Connect to the Internet to see the quote"
            binding.motivationAuthor.text = "Confucius"
            binding.progressBar.visibility = View.GONE

        }
    }

    // changes phrase based on the system's language. Only russian and english are available on the server
    private fun checkLanguage(): String {
        return if (Locale.getDefault().language == "ru") "ru"
        else "en"
    }


    private fun insertMotivation() = lifecycleScope.launch {
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


    /** database block  */

    private fun insertDataToDatabase() {
        val task = Task(
            0,
            title = binding.editTextTitle.text.toString(),
            describtion = binding.editTextDescrib.text.toString()
        )

        //action
        if (checkTitle()) {
            viewModel.insert(task)
            Toast.makeText(requireContext(), R.string.new_added, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_newTaskFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), R.string.please_fill, Toast.LENGTH_SHORT).show()

        }
    }

    //check that title is not empty
    private fun checkTitle(): Boolean {
        if (binding.editTextTitle.text?.isEmpty()!!) {
            return false
        }
        return true
    }

}


