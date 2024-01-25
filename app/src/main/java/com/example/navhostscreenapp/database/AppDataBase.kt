package com.example.navhostscreenapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.navhostscreenapp.database.dao.QuestionDao
import com.example.navhostscreenapp.database.dao.RecognitionResultDao
import com.example.navhostscreenapp.database.entity.Question
import com.example.navhostscreenapp.database.entity.RecognitionResult

@Database(entities = [Question::class,RecognitionResult::class], version = 2, exportSchema = false)
abstract class AppDataBase:RoomDatabase() {

    abstract fun questionDao():QuestionDao

    abstract fun recognitionDao():RecognitionResultDao




    companion object{
        private var INSTANCE:AppDataBase? = null

        fun getDatabase(context:Context):AppDataBase {
            return INSTANCE?: synchronized(this) {
                Room.databaseBuilder(context,AppDataBase::class.java,"database").
                        fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }

}