package com.boss.mytodo.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.boss.mytodo.R
import com.boss.mytodo.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)

        binding.apply {
            tvEmail.setOnClickListener { sendEmail() }
            btnGit.setOnClickListener { findNavController().navigate(R.id.webFragment) }
        }

        return binding.root
    }


    private fun sendEmail() {
        try {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "paul.sizon@outlook.com", null
                )
            )
            startActivity(Intent.createChooser(emailIntent, getString(R.string.chooseEmail)))
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }


}
