package com.company.myapplication.fragment

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.company.myapplication.R
import com.company.myapplication.URIPathHelper
import com.company.myapplication.databinding.SelectVideoBinding

const val REQUEST_CODE = 200
class VideoFragment: Fragment() {

    private var _binding: SelectVideoBinding? = null
    private val binding get() = _binding!!
    private lateinit var videoFullPath: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SelectVideoBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.videoView.setOnClickListener {
            openGalleryForVideo()
            binding.videoView.isEnabled = false
        }
        binding.nextButton.isEnabled = false

        binding.nextButton.setOnClickListener {
            moveToImageFragment()
        }
        Glide
            .with(requireContext())
            .load(ResourcesCompat.getDrawable(resources, R.drawable.upload_video, null))
            .fitCenter()
            .centerCrop()
            .into(binding.videoView)

    }

    private fun openGalleryForVideo() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(Intent.createChooser(intent, "Select Video"),REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            if (data?.data != null) {
                Log.e("TAG", "onActivityResult: data not null ${data.data}", )
                val uriPathHelper = URIPathHelper()
                videoFullPath = uriPathHelper.getPath(requireContext(), data.data!!).toString() // Use this video path according to your logic
                // if you want to play video just after picking it to check is it working
                Log.e("TAG", "onActivityResult: $videoFullPath", )
                if (videoFullPath != null) {
                    //playVideoInDevicePlayer(videoFullPath);
                    Log.e("TAG", "onActivityResult: $videoFullPath", )

                    Glide
                        .with(requireContext())
                        .load(videoFullPath)
                        .fitCenter()
                        .centerCrop()
                        .into(binding.videoView)

                    binding.videoView.isEnabled = true
                    binding.nextButton.isEnabled = true
                }
            }
        }
    }

    private fun moveToImageFragment(){
        lifecycleScope.launchWhenStarted {
            Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(
                VideoFragmentDirections.actionVideoToImage(videoFullPath)
            )
        }
    }

}