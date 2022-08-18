package com.company.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import com.bumptech.glide.Glide
import com.company.myapplication.R
import com.company.myapplication.databinding.ActivityPlayVideoBinding

class PlayVideo : AppCompatActivity() {

    private lateinit var binding: ActivityPlayVideoBinding
    var mediaControls: MediaController? = null
    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoUri = intent.getStringExtra("videoUri")
        val imageUri = intent.getStringExtra("imageUri")
        val tag = intent.getStringExtra("tag")


        Glide
            .with(this)
            .load(imageUri)
            .circleCrop()
            .into(binding.imageShow)


        binding.tagShow.text = tag

        videoView = binding.videoPlay

        if (mediaControls == null) {
            // creating an object of media controller class
            mediaControls = MediaController(this)

            // set the anchor view for the video view
            mediaControls!!.setAnchorView(videoView)
        }

        videoView.setMediaController(mediaControls)
        videoView.setVideoPath(videoUri)
        videoView.requestFocus()
        videoView.start()
        videoView!!.setOnCompletionListener {
            Toast.makeText(this, "Video completed",
                Toast.LENGTH_LONG).show()
        }

        // display a toast message if any
        // error occurs while playing the video
        videoView!!.setOnErrorListener { mp, what, extra ->
            Toast.makeText(this, "An Error Occurred " +
                    "While Playing Video !!!", Toast.LENGTH_LONG).show()
            false
        }
    }
}