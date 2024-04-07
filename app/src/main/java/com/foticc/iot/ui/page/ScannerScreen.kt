package com.foticc.iot.ui.page

import TextRecViewModel
import android.content.ContentValues
import android.graphics.Paint
import android.graphics.Typeface
import android.provider.MediaStore
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.Analyzer
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.foticc.iot.database.entity.Question
import com.foticc.iot.text.EngineCollect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executors

@Composable
fun TwoPage(vm: HomePageViewModel = viewModel(factory = ViewModelProvider.Factory)) {
    val rememberCoroutineScope = rememberCoroutineScope()
    Button(onClick = {
        rememberCoroutineScope.launch {
            vm.questionDao.insert(Question(seq = 1, context = "123123123"))
        }
//        CoroutineScope(Dispatchers.IO).launch {
//
//        }
    }) {
        Text(text = "click")
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScannerScreen() {
    val context = LocalContext.current
    val rememberPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.CAMERA)
    val lifecycleOwner = LocalLifecycleOwner.current
    if (rememberPermissionState.status.isGranted) {
        ShowRecView()
    } else {
        Text(text = "no camera permission")
    }

    DisposableEffect(Unit) {
        val lifecycleEventObserver = LifecycleEventObserver { source, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    if (!rememberPermissionState.status.isGranted) {
                        rememberPermissionState.launchPermissionRequest()
                    }
                }

                else -> {

                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleEventObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleEventObserver)
        }
    }

}

@Composable
fun CameraView1(analyzer: Analyzer, takePictureCallback:ImageCapture.OnImageSavedCallback) {
    val lifecycle = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraExecutor = Executors.newSingleThreadExecutor()
    val processCameraProviderListenableFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    /**
     * If you use the CameraX API, be sure that backpressure strategy is set to its default value
     * ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST. This guarantees only one image will be delivered
     * for analysis at a time. If more images are produced when the analyzer is busy, they will
     * be dropped automatically and not queued for delivery. Once the image being analyzed is closed
     * by calling ImageProxy.close(), the next latest image will be delivered.
     */
    /**
     * If you use the Camera2 API, capture images in format.
     *  If you use the older Camera API, capture images in format. ImageFormat.YUV_420_888ImageFormat.NV21
     *
     */
    val imageAnalysis = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .setTargetResolution(android.util.Size(1080,1800))
        .build()
        .also {
            it.setAnalyzer(cameraExecutor,analyzer)
        }

    val name = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.CHINA)
        .format(System.currentTimeMillis())

    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
    }


    val outputOptions = ImageCapture.OutputFileOptions.Builder(
        context.contentResolver,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    ).build()

    val imageCapture = remember {
        ImageCapture.Builder().build()
    }

    Box {
        AndroidView(modifier = Modifier.fillMaxSize(), factory = { ctx ->
            val preview = PreviewView(ctx)
            val executor = ContextCompat.getMainExecutor(ctx)
            processCameraProviderListenableFuture.addListener({
                val cameraProvider = processCameraProviderListenableFuture.get()
                bindPreview(lifecycle, preview, cameraProvider, imageCapture, imageAnalysis)
            }, executor)
            preview
        })
        IconButton(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 20.dp),onClick = {
            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context), takePictureCallback
            )
        }) {
            Icon(imageVector = Icons.Filled.Notifications,contentDescription = "save", tint = Color.Red, modifier = Modifier
                .width(20.dp)
                .height(20.dp))
        }
    }


}


@Composable
fun ShowRecView(vm:TextRecViewModel = viewModel()) {
    val context = LocalContext.current
    val imageAnalysisListener = ImageAnalysisProxy(
        onSuccessListener = {
            vm.setText(it.text)
            Log.i("tg:",it.text)
            vm.setTextObj(it)
        },
        onFailureListener = {
            it.printStackTrace()
        }
    )
    Box(modifier = Modifier.fillMaxSize()) {
        CameraView1(imageAnalysisListener, takePictureCallback =  object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = outputFileResults.savedUri
                val msg = "Photo capture succeeded: $savedUri"
                vm.saveTempData(savedUri)
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }

            override fun onError(exception: ImageCaptureException) {
                exception.printStackTrace()
                Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
            }
        })
        TextMask(modifier = Modifier.fillMaxSize(),vm.textObj.value)

    }

}


@androidx.annotation.OptIn(ExperimentalGetImage::class)
@Composable
fun CameraView(vm: TextRecViewModel = viewModel()) {
    val lifecycle = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraExecutor = Executors.newSingleThreadExecutor()
    LaunchedEffect(Unit) {

    }
    val processCameraProviderListenableFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    val imageAnalysisListener = ImageAnalysisProxy(
        onSuccessListener = {
            vm.setTextObj(it)
            vm.setText(it.text)
        },
        onFailureListener = {

        }
    )

    val imageCapture = ImageCapture.Builder().build()




    /**
     * If you use the CameraX API, be sure that backpressure strategy is set to its default value
     * ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST. This guarantees only one image will be delivered
     * for analysis at a time. If more images are produced when the analyzer is busy, they will
     * be dropped automatically and not queued for delivery. Once the image being analyzed is closed
     * by calling ImageProxy.close(), the next latest image will be delivered.
     */
    /**
     * If you use the Camera2 API, capture images in format.
     *  If you use the older Camera API, capture images in format. ImageFormat.YUV_420_888ImageFormat.NV21
     *
     */
    val imageAnalysis = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .setTargetResolution(android.util.Size(1080,1800))
        .build()
        .also {
            it.setAnalyzer(cameraExecutor,imageAnalysisListener)
        }


    val name = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.CHINA)
        .format(System.currentTimeMillis())

    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
    }


    val outputOptions = ImageCapture.OutputFileOptions.Builder(
        context.contentResolver,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    ).build()



    Box(modifier = Modifier.fillMaxSize()) {

        AndroidView(modifier = Modifier.fillMaxSize(), factory = { ctx ->
            val preview = PreviewView(ctx)
            val executor = ContextCompat.getMainExecutor(ctx)
            processCameraProviderListenableFuture.addListener({
                val cameraProvider = processCameraProviderListenableFuture.get()
                bindPreview(lifecycle, preview, cameraProvider, imageCapture,imageAnalysis)
            }, executor)
            preview
        })

//        val enableRecognition = vm.enableRecognition
//        if (enableRecognition) {
            TextMask(modifier = Modifier.fillMaxSize(),vm.textObj.value)
//        }


        CameraToolsBar(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 20.dp),
            pause = {
            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val savedUri = outputFileResults.savedUri
                        val msg = "Photo capture succeeded: $savedUri"
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
                    }
                })
        }, switch = {
            vm.switchRecognition(it)
        }, star = {
            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val savedUri = outputFileResults.savedUri
                        val msg = "Photo capture succeeded: $savedUri"
                        vm.saveTempData(savedUri)
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
                    }
                })
        })

    }

}

private fun bindPreview(
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
    cameraProvider: ProcessCameraProvider,
    imageCapture: ImageCapture,
    imageAnalysis: ImageAnalysis
) {
    val preview = Preview.Builder().build()
    val cameraSelector =
        CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
    preview.setSurfaceProvider(previewView.surfaceProvider)
    val camera = cameraProvider.bindToLifecycle(
        lifecycleOwner,
        cameraSelector,
        preview,
        imageCapture,
        imageAnalysis
    )
    camera.cameraControl.cancelFocusAndMetering()
}


@Composable
fun TextMask(modifier: Modifier,textObj:Text?) {
    if (textObj == null) {
        return;
    }
    val textBlocks = textObj.textBlocks
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()){
            for (block in textBlocks) {
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
                    drawTextA(text,x = rect.centerX().toFloat(), y= rect.centerY().toFloat(),
                            width = rect.width())
                }
            }
        }
    }
}

fun DrawScope.drawTextA(str:String, x:Float, y:Float, width: Int) {
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

@Composable
fun CameraToolsBar(modifier: Modifier, pause: () -> Unit,switch:(Boolean)->Unit,star:()->Unit) {
    var status by rememberSaveable {
        mutableStateOf(false)
    }
    Column(modifier = modifier) {
        TextButton(onClick = pause) {
            Text(text = "TAKE PIC")
        }
        Switch(checked = status, onCheckedChange = {
            status = it
            switch(it)
        })
        TextButton(onClick = star) {
            Text(text = "take")
        }


    }
}




private class ImageAnalysisProxy(
    val onSuccessListener: (Text) -> Unit,
    val onFailureListener: (Exception) -> Unit,
) : ImageAnalysis.Analyzer {

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            EngineCollect.textRecognizer.process(image)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)
                .addOnCompleteListener {
                    // 注意关闭 啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊
                    mediaImage.close()
                    imageProxy.close()
                }

        }
    }

}


