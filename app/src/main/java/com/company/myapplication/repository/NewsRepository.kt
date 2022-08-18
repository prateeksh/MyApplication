package com.company.myapplication.repository

import androidx.lifecycle.LiveData
import com.company.myapplication.model.News
import com.company.myapplication.room.NewsDatabase

class NewsRepository (
    private val newsDatabase: NewsDatabase
        ) {

    suspend fun insertNews(news: News) = newsDatabase.userDao().insertNews(news)

    suspend fun deleteNews(videoUri: String) = newsDatabase.userDao().deleteNews(videoUri)

    fun getAllNews(): LiveData<List<News>> = newsDatabase.userDao().getNews()
}