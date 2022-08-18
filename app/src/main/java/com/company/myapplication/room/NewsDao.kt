package com.company.myapplication.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.company.myapplication.model.News

@Dao
interface NewsDao {

    @Insert
    fun insertNews(news: News)

    @Query(value = "SELECT * FROM news")
    fun getNews(): LiveData<List<News>>

    @Query(value = "DELETE FROM news WHERE videoUri = :videoUri")
    fun deleteNews(videoUri: String)
}