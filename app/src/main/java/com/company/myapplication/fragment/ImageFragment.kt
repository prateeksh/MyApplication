package com.company.myapplication.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.company.myapplication.R
import com.company.myapplication.databinding.SelectImageBinding

class ImageFragment: Fragment() {

    private var _binding: SelectImageBinding? = null
    private val binding get() = _binding!!

    private val args: ImageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SelectImageBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextButtonImage.isEnabled = false
        binding.imageView.setOnClickListener {
            openGalleryForImages()
        }

        Glide
            .with(requireContext())
            .load(ResourcesCompat.getDrawable(resources, R.drawable.upload_image, null))
            .circleCrop()
            .into(binding.imageView)
    }


    private fun openGalleryForImages() {

        if (Build.VERSION.SDK_INT < 19) {
            val intent = Intent()
            intent.type = "image/*"
           // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Choose Pictures")
                , REQUEST_CODE
            )
        }
        else { // For latest versions API LEVEL 19+
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
           // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE);
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){

             if (data?.getData() != null){
                Log.e("TAG", "onActivityResult Image: ${data.data}", )
                val imageUri: Uri = data.data!!
                Log.e("TAG", "onActivityResult Image: $imageUri", )
                Glide
                    .with(requireContext())
                    .load(imageUri)
                    .circleCrop()
                    .into(binding.imageView)

                 binding.nextButtonImage.isEnabled = true
                 getDataAndMoveToView(imageUri)
            }
        }
    }

    private fun getDataAndMoveToView(imageUri: Uri){
        binding.nextButtonImage.setOnClickListener {
            val name = binding.name.text.toString()
            val tag = binding.tag.text.toString()
            Log.e("TAG", "getDataAndMoveToView: $name, $tag", )
            if (name.isNotEmpty() && tag.isNotEmpty()){
                lifecycleScope.launchWhenStarted {
                    Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(
                        ImageFragmentDirections.actionImageToView(name, tag, args.rootDirectory, imageUri.toString())
                    )
                }
            }else{
                binding.name.error = " Name Cannot be null"
                binding.tag.error = "Tag Cannot be null"
            }

        }

    }
}