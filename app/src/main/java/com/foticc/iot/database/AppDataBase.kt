package com.foticc.iot.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.foticc.iot.database.dao.QuestionDao
import com.foticc.iot.database.dao.RecognitionResultDao
import com.foticc.iot.database.entity.Question
import com.foticc.iot.database.entity.RecognitionResult

@Database(entities = [Question::class, RecognitionResult::class], version = 2, exportSchema = false)
abstract class AppDataBase:RoomDatabase() {

    abstract fun questionDao(): QuestionDao

    abstract fun recognitionDao(): RecognitionResultDao




    companion object{
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context:Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, AppDataBase::class.java,"database").
                        fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }

}