package com.example.mytodo.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.mytodo.R
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        // send me an email
        view.findViewById<TextView>(R.id.email_adress).setOnClickListener { sendEmail()
        }

        return view
    }


    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data =
            Uri.parse("mailto:paul.sizon@outlook.com")
        //intent.putExtra(Intent.EXTRA_EMAIL, "")
        try {
            startActivity(Intent.createChooser(intent, "Choose Email Client..."))
            // startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }


}
