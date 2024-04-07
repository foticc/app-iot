

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foticc.iot.ScanApplication
import com.foticc.iot.database.dao.RecognitionResultDao
import com.foticc.iot.database.entity.RecognitionResult
import com.google.mlkit.vision.text.Text
import kotlinx.coroutines.launch

class TextRecViewModel():ViewModel() {

    var textObj:MutableState<Text?> = mutableStateOf(null)

    val text:MutableState<String> = mutableStateOf("")

    var enableRecognition by mutableStateOf(true)

    val recognitionResultDao: RecognitionResultDao = ScanApplication.getDatabase().recognitionDao();


    fun switchRecognition(enable:Boolean) {
        enableRecognition = enable
    }

    fun saveTempData(uri:Uri?) {
        checkNotNull(uri)
        viewModelScope.launch {
            recognitionResultDao.insert(RecognitionResult(uri=uri.toString(), content = text.value))
        }
    }




    fun setRecognition(uri:Uri) {
//        viewModelScope.launch {
//            val fromFilePath = InputImage.fromFilePath(application, uri)
//            val addOnSuccessListener = EngineCollect.textRecognizer.process(fromFilePath)
//                .addOnSuccessListener { t ->
//                    text.value = t.toString()
//
//                }
//            recognitionResultDao.insert(RecognitionResult(uri=uri.toString(), content = addOnSuccessListener.result.text))
//        }
    }

    fun setText(str:String) {
        Log.e("setText",str)
        text.value = str
    }

    fun setTextObj(obj:Text) {
        this.textObj.value = obj
    }


}




