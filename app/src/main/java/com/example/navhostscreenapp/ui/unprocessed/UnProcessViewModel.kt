package com.example.navhostscreenapp.ui.unprocessed

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.navhostscreenapp.ScanApplication
import com.example.navhostscreenapp.database.dao.RecognitionResultDao
import com.example.navhostscreenapp.text.EngineCollect
import com.google.mlkit.nl.entityextraction.EntityAnnotation

class UnProcessViewModel:ViewModel() {


    val recognitionResultDao: RecognitionResultDao = ScanApplication.getDatabase().recognitionDao();


    var entityAnnotationList:MutableState<List<EntityAnnotation>> = mutableStateOf(emptyList())

    fun decode(entityAnnotationList:List<EntityAnnotation>) {
        this.entityAnnotationList.value = entityAnnotationList
    }
}