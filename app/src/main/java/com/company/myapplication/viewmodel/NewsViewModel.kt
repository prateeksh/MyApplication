package com.company.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.company.myapplication.repository.NewsRepository
import com.company.myapplication.model.News

class NewsViewModel(
    private val repository: NewsRepository
) : ViewModel() {


    suspend fun insertNote(news: News) = repository.insertNews(news)

    suspend fun getAllNews() = repository.getAllNews()

    suspend fun deleteNote(videoUri: String) = repository.deleteNews(videoUri)
}