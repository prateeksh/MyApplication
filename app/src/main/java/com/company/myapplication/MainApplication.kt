package com.company.myapplication

import android.app.Application
import com.company.myapplication.repository.NewsRepository
import com.company.myapplication.room.NewsDatabase

class MainApplication: Application() {

    lateinit var repository: NewsRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize(){
        val database = NewsDatabase.getDatabase(applicationContext)
        repository = NewsRepository(database)
    }
}