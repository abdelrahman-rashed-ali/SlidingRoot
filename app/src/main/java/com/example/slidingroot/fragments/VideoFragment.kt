package com.example.slidingroot.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.example.slidingroot.R
import com.example.slidingroot.classes.MealData


class VideoFragment : Fragment() {
    private lateinit var saveView : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveView = view
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onStart() {
        super.onStart()
        val webView = saveView.findViewById<WebView>(R.id.webView)
        val rawVideoUrl = MealData.getVideoURL()
        val videoId = rawVideoUrl.substringAfter("v=").substringBefore("&")
        val embedUrl = "https://www.youtube.com/embed/$videoId"

        val video = """
                        <iframe width="100%" height="100%" src="$embedUrl" 
                        title="YouTube video player" frameborder="0" 
                        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" 
                        referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
                    """.trimIndent()
        webView.loadData(video,"text/html","utf-8")
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
    }
}