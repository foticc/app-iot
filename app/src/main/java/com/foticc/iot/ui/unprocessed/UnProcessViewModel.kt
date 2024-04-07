package com.foticc.iot.ui.unprocessed

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.foticc.iot.ScanApplication
import com.foticc.iot.database.dao.RecognitionResultDao
import com.google.mlkit.nl.entityextraction.EntityAnnotation

class UnProcessViewModel:ViewModel() {


    val recognitionResultDao: RecognitionResultDao = ScanApplication.getDatabase().recognitionDao();


    var entityAnnotationList:MutableState<List<EntityAnnotation>> = mutableStateOf(emptyList())

    fun decode(entityAnnotationList:List<EntityAnnotation>) {
        this.entityAnnotationList.value = entityAnnotationList
    }
}