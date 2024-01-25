package com.example.navhostscreenapp

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.example.navhostscreenapp.database.AppDataBase
import com.google.mlkit.nl.entityextraction.EntityExtraction
import com.google.mlkit.nl.entityextraction.EntityExtractionParams
import com.google.mlkit.nl.entityextraction.EntityExtractor
import com.google.mlkit.nl.entityextraction.EntityExtractorOptions

class ScanApplication:Application() {

    companion object {
        lateinit var dataBase: AppDataBase

        fun getDatabase():AppDataBase {
            return dataBase;
        }
    }

    override fun onCreate() {
        super.onCreate()
        dataBase = AppDataBase.getDatabase(this);
        val entityExtractor =
            EntityExtraction.getClient(
                EntityExtractorOptions.Builder(EntityExtractorOptions.CHINESE)
                    .build())
        entityExtractor.downloadModelIfNeeded()
            .addOnSuccessListener {
                Toast.makeText(this,"addOnSuccessListener",Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(this,"addOnFailureListener",Toast.LENGTH_LONG).show()
            }.addOnCompleteListener {
                entityExtractor.close()
            }
    }

}