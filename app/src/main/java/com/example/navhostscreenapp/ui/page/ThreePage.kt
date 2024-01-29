package com.example.navhostscreenapp.ui.page

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.net.Uri
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.navhostscreenapp.text.EngineCollect
import com.example.navhostscreenapp.ui.theme.AppiotTheme
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import kotlinx.coroutines.launch


class TextObjViewModel:ViewModel(){
    val textObj:MutableState<Text?> = mutableStateOf(null)


    fun setTextObj(text: Text) {
        textObj.value = text
        Log.i("setTextObj", text.text)
    }

    fun setInputInfo(image: InputImage) {

    }

    fun decodeText(uri:Uri?) {

    }
}

@Composable
fun ThreePage(viewModel: TextObjViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val context = LocalContext.current
    var text by remember {
        mutableStateOf("")
    }
    val rememberCoroutineScope = rememberCoroutineScope()


    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.type = "image/*"
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    val startActivityForResult =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { it ->
            rememberCoroutineScope.launch {
                if (it.resultCode == Activity.RESULT_OK) {
                    val image = InputImage.fromFilePath(context, it.data?.data!!)
                    EngineCollect.textRecognizer.process(image).addOnSuccessListener {
                            t->
                        text = t.text
                        viewModel.setTextObj(t)
                    }.addOnFailureListener {
                        text = it.message.toString()
                    }.addOnCompleteListener {

                    }
                } else {
                    Toast.makeText(context, "nothing select", Toast.LENGTH_LONG).show()
                }
            }
        }
    Row {
//        Text(text = text)
        ResultView(viewModel)
        TextButton(onClick = {
            startActivityForResult.launch(intent)
        }) {
            Text(text = "onClick")
        }
    }

}

@Composable
fun ResultView(viewModel: TextObjViewModel) {
    if (viewModel.textObj.value == null) {
        return
    }
    val textMeasurer = rememberTextMeasurer()
    val textBlocks = viewModel.textObj.value!!.textBlocks
    Canvas(modifier = Modifier.fillMaxSize()){
        for (block in textBlocks) {
//            val rect = block.boundingBox
            val text = block.text
            val cornerPoints = block.cornerPoints
            val lines = block.lines
            val rect = block.boundingBox
            if (rect!=null) {
                    drawRect(
                        Color.Red, topLeft = Offset(rect.left.toFloat(), rect.top.toFloat()),
                        size = Size(rect.width().toFloat(), rect.height().toFloat()),
                        style = Stroke(2f)
                    )
                    drawText(text,x = rect.centerX().toFloat(), y= rect.centerY().toFloat(),
                            width = rect.width())
//                drawText(textMeasurer,text,topLeft = Offset(rect.centerX().toFloat(), rect.centerY().toFloat()))

            }
//            for (line in lines) {
//                val elements = line.elements
//                val rect = line.boundingBox
//                if (rect!=null) {
//                    drawRect(
//                        Color.Red, topLeft = Offset(rect.left.toFloat(), rect.top.toFloat()),
//                        size = Size(rect.width().toFloat(), rect.height().toFloat()),
//                        style = Stroke(2f)
//                    )
//                    drawText(text,topLeft = Offset(rect.left.toFloat(), rect.top.toFloat()),
//                        Size(rect.width().toFloat(), rect.height().toFloat()))
//
//                }
//            }
//            val path = Path()
//            cornerPoints?.forEachIndexed{
//                index, point ->
//                if (index == 0) {
//                    path.moveTo(point.x.toFloat(),point.y.toFloat())
//                }else{
//                    path.lineTo(point.x.toFloat(),point.y.toFloat())
//                }
//            }
//            drawPath(path = path, color = Color.Red)

        }
    }
}

private fun textHandler(text: Text) {
    val textBlocks = text.textBlocks
    for (block in textBlocks) {
        val blockText = block.text
        val blockCornerPoints = block.cornerPoints
        // rect
        val blockFrame = block.boundingBox
//        for (line in block.lines) {
//            val lineText = line.text
//            val lineCornerPoints = line.cornerPoints
//            val lineFrame = line.boundingBox
//            for (element in line.elements) {
//                val elementText = element.text
//                val elementCornerPoints = element.cornerPoints
//                val elementFrame = element.boundingBox
//            }
//        }
    }
}

fun drawText(str: String) {


}

fun DrawScope.drawText(str:String,x:Float,y:Float,width: Int) {
    val textPaint = Paint().apply {
        isAntiAlias = true
        isDither = true
        typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        textAlign = Paint.Align.CENTER
    }
    textPaint.color = android.graphics.Color.RED
    textPaint.textSize = 20f
    val fontMetrics = textPaint.fontMetrics
    val top = fontMetrics.top
    val bottom = fontMetrics.bottom
    //拿到原生的 Canvas 对象
    val nativeCanvas = this.drawContext.canvas.nativeCanvas
//    nativeCanvas.drawText(
//        str,
//        topLeft.x, topLeft.y, textPaint
//    )
    val save = nativeCanvas.save()
    nativeCanvas.translate(x,y)

    val staticLayout = StaticLayout.Builder.obtain(str, 0,str.length,
        TextPaint(textPaint), width)
        .setAlignment(Layout.Alignment.ALIGN_NORMAL)
        .setIncludePad(false)
        .build()
    staticLayout.draw(nativeCanvas)
    nativeCanvas.restoreToCount(save)
//    nativeCanvas.drawText(str,x,y,textPaint)
}


@Preview
@Composable
fun Demo() {
    AppiotTheme {
        val rect = Rect(0, 0, 100, 100)
        Box(modifier = Modifier.fillMaxSize()) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(
                    Color.Red, topLeft = Offset(rect.left.toFloat(), rect.top.toFloat()),
                    size = Size(rect.right.toFloat(), rect.bottom.toFloat()),
                    style = Stroke(2f)
                )
                drawIntoCanvas {
                    //将 Jetpack Compose 环境的 Paint 对象转换为原生的 Paint 对象
                    val textPaint = Paint().apply {
                        isAntiAlias = true
                        isDither = true
                        typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                        textAlign = Paint.Align.CENTER
                    }
                    textPaint.color = android.graphics.Color.RED
                    textPaint.textSize = 50f
                    val fontMetrics = textPaint.fontMetrics
                    val top = fontMetrics.top
                    val bottom = fontMetrics.bottom
                    val centerX = size.width / 2f
                    val centerY = size.height / 2f - top / 2f - bottom / 2f
                    //拿到原生的 Canvas 对象
                    val nativeCanvas = it.nativeCanvas
                    nativeCanvas.drawText(
                        "学不动也要学",
                        centerX, centerY, textPaint
                    )
                    val text =
                        "在Android开发中，Canvas.drawText不会换行，即使一个很长的字符串也只会显示一行，超出部分会隐藏在屏幕之外.StaticLayout是android中处理文字的一个工具类，StaticLayout 处理了文字换行的问题"
                    val staticLayout = StaticLayout.Builder.obtain(text, 0,text.length,
                        TextPaint(textPaint), 200)
                        .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                        .setIncludePad(false)
                        .build()

                    staticLayout.draw(nativeCanvas)
                }

            }
        }
    }
}