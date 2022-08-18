package com.company.myapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.company.myapplication.MainApplication
import com.company.myapplication.activity.SavedActivity
import com.company.myapplication.databinding.ViewInfoBinding
import com.company.myapplication.model.News
import com.company.myapplication.viewmodel.NewsViewModel
import com.company.myapplication.viewmodel.NewsViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewFragment : Fragment(){

    private var _binding: ViewInfoBinding? = null
    private val binding get() = _binding!!

    private val args: ViewFragmentArgs by navArgs()

    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ViewInfoBinding.inflate(inflater, container, false)

       val repository = (requireActivity().application as MainApplication).repository

        newsViewModel =
            ViewModelProvider(requireActivity(), NewsViewModelFactory(repository))
                .get(NewsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = args.name
        val tag = args.tag
        val imageUri = args.imageUri
        val videoUri = args.videoUri

        binding.tagView.text = tag

        Glide
            .with(requireContext())
            .load(imageUri)
            .circleCrop()
            .into(binding.userImage)

        Log.e("TAG", "onViewCreated: $name $tag $imageUri $videoUri" )
        Glide
            .with(requireContext())
            .load(videoUri)
            .centerCrop()
            .fitCenter()
            .into(binding.videoThumbnail)




        binding.save.setOnClickListener {
            val news = News(id = null, videoUri = videoUri, imageUri = imageUri, name = name, tag = tag)
            CoroutineScope(Dispatchers.IO).launch {
                newsViewModel.insertNote(news)
                val snack = Snackbar.make(view,"Inserted in db", Snackbar.LENGTH_SHORT)
                snack.show()
                val intent = Intent(requireContext(), SavedActivity::class.java)
                startActivity(intent)
            }

        }
    }
}