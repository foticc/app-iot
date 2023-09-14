package com.example.navhostscreenapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val seq: Int = 0,
    val context: String?
)
