package com.foticc.iot.text

import com.google.mlkit.nl.entityextraction.EntityExtraction
import com.google.mlkit.nl.entityextraction.EntityExtractorOptions
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions

class EngineCollect {

    companion object {
        val textRecognizer = TextRecognition.getClient(ChineseTextRecognizerOptions.Builder().build())

        val entityExtractor =  EntityExtraction.getClient(
            EntityExtractorOptions.Builder(EntityExtractorOptions.ENGLISH)
                .build())

    }

}