package com.company.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.myapplication.MainApplication
import com.company.myapplication.adapter.NewsAdapter
import com.company.myapplication.databinding.ActivitySavedBinding
import com.company.myapplication.viewmodel.NewsViewModel
import com.company.myapplication.viewmodel.NewsViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedActivity : AppCompatActivity() {

    private var _binding: ActivitySavedBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySavedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.savedItemRecycler

        val repository = (application as MainApplication).repository

        newsViewModel =
            ViewModelProvider(this, NewsViewModelFactory(repository))
                .get(NewsViewModel::class.java)

        showData()

    }

    private fun showData() {
        CoroutineScope(Dispatchers.Main).launch {
            newsViewModel.getAllNews().observe(this@SavedActivity) { notes ->

                adapter = NewsAdapter(notes, this@SavedActivity)
                recyclerView.adapter = adapter
                recyclerView.layoutManager =
                    LinearLayoutManager(this@SavedActivity, RecyclerView.VERTICAL, false)

                adapter.onItemClick = {
                    val intent = Intent(this@SavedActivity, PlayVideo::class.java)
                    intent.putExtra("videoUri", it.videoUri)
                    intent.putExtra("imageUri", it.imageUri)
                    intent.putExtra("tag", it.tag)
                    startActivity(intent)
                }
            }
        }
    }
}