package com.foticc.iot.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecognitionResult(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val uri:String,
    val content:String
)
