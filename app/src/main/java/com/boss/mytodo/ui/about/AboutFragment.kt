package com.boss.mytodo.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.boss.mytodo.R

class AboutFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        // send me an email
        view.findViewById<TextView>(R.id.email_adress).setOnClickListener { sendEmail() }
        return view
    }


    private fun sendEmail() {
        try {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "paul.sizon@outlook.com", null
                )
            )
            startActivity(Intent.createChooser(emailIntent, "Choose Email Client..."))
        } catch (e: Exception) {
            //if any thing goes wrong for boss no email client application or any exception
            //get and show exception message
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }


}
