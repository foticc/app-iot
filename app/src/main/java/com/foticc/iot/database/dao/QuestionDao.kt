package com.foticc.iot.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.foticc.iot.database.entity.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Question)

    @Update
    suspend fun update(item: Question)

    @Delete
    suspend fun delete(item: Question)

    @Query("SELECT * from question WHERE id = :id")
    fun select(id: Int): Flow<Question>

    @Query("SELECT * from question ORDER BY id desc")
    fun selectAll(): Flow<List<Question>>

    @Query("SELECT * from question where context like '%'||:condition||'%' ORDER BY id desc")
    fun selectAll(condition:String):Flow<List<Question>>
}