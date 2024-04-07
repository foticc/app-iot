package com.foticc.iot.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val seq: Int = 0,
    val context: String?
)
