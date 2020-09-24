package com.example.mytodo

import android.os.Bundle
import android.provider.Settings.Global.putString
import android.provider.Settings.Secure.putString
import android.provider.Settings.System.putString
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController


class NewTaskFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_task, null);

        val buttonDone = view.findViewById<Button>(R.id.button_done)
        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        buttonDone.setOnClickListener {
            view.findNavController().navigate(R.id.action_newTaskFragment_to_listFragment)



        }

        return view
    }



}