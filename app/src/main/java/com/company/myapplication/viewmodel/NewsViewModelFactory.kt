package com.company.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.myapplication.repository.NewsRepository

class NewsViewModelFactory(
    private val repository: NewsRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            val constructor = modelClass.getDeclaredConstructor(NewsRepository::class.java)
            return constructor.newInstance(repository)
        } catch (e: Exception) {
            e.stackTrace
        }
        return super.create(modelClass)
    }
}