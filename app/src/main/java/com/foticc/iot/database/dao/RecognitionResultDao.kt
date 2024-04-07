package com.foticc.iot.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.foticc.iot.database.entity.RecognitionResult
import kotlinx.coroutines.flow.Flow

@Dao
interface RecognitionResultDao {

    @Insert
    suspend fun insert(recognitionResult: RecognitionResult)

    @Query("SELECT * from recognitionResult ORDER BY id desc")
    fun selectAll(): Flow<List<RecognitionResult>>

}