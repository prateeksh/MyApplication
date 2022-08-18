package com.company.myapplication.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.company.myapplication.model.News

@Database(entities = [News::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun userDao(): NewsDao

    companion object{
        private var INSTANCE: NewsDatabase ?= null

        fun getDatabase(context: Context): NewsDatabase{

            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    NewsDatabase::class.java,
                    "newsDB"
                ).build()
            }

            return INSTANCE!!
        }
    }
}