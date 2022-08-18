package com.company.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
class News(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int?,
    @ColumnInfo(name = "videoUri") var videoUri: String?               = null,
    @ColumnInfo(name = "imageUri") var imageUri: String?               = null,
    @ColumnInfo(name = "name") var name: String?               = null,
    @ColumnInfo(name = "tag") var tag: String?               = null,
)