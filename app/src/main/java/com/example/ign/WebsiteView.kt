package com.example.ign

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.website_view.*

class WebsiteView: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.website_view)

        val url = intent.getSerializableExtra("URL")
        val type = intent.getSerializableExtra("type")
        val videoView = findViewById<VideoView>(R.id.video_view)
        val imageView = findViewById<ImageView>(R.id.image_view)
        val webView =findViewById<WebView>(R.id.web_view)

        if(type == "MP4"){
                imageView.visibility = View.GONE
                webView.visibility = View.GONE
                val mediaController = MediaController(this)
                mediaController.setAnchorView(videoView)
                val video = Uri.parse(url.toString())
                videoView.setMediaController(mediaController)
                videoView.setVideoURI(video)
                videoView.start()
        }
        else if(type == "picture"){
            videoView.visibility = View.GONE
            webView.visibility = View.GONE
            Glide.glideFetch(url.toString(), url.toString(), imageView)
        }
        else if(type == "link"){
            imageView.visibility = View.GONE
            videoView.visibility = View.GONE
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
            webView.loadUrl(url.toString())
        }
    }
}