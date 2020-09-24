package com.example.mytodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, null);

        val floatingButton: FloatingActionButton = view.findViewById(R.id.floatingActionButton)
        floatingButton.setOnClickListener {view.findNavController().navigate(R.id.action_listFragment_to_newTaskFragment) }

        val textTask = view.findViewById<TextView>(R.id.textTask)


        return view
    }

}