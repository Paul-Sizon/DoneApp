package com.boss.mytodo.ui.fragments.web

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.boss.mytodo.databinding.FragmentWebBinding


class WebFragment : Fragment() {
    private lateinit var binding: FragmentWebBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebBinding.inflate(inflater, container, false)

        binding.apply {
            webview.webViewClient = WebViewClient()
            webview.settings.javaScriptEnabled = true
            webview.loadUrl("https://github.com/Paul-Sizon")
        }

        return binding.root
    }


}