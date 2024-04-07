package com.foticc.iot.ui.unprocessed

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.foticc.iot.text.EngineCollect
import com.google.mlkit.nl.entityextraction.EntityAnnotation
import com.google.mlkit.nl.entityextraction.EntityExtractionParams


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnprocessedItem(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "back")
                    }
                },
                actions = {

                })
        }
    ) {
        Item(modifier = Modifier
            .padding(it)
            .padding(start = 100.dp))
    }

}
fun show(context:Context,msg:String) {
    Toast.makeText(context,msg,Toast.LENGTH_LONG).show()
}

@Composable
private fun Item(modifier: Modifier = Modifier,vm: UnProcessViewModel = viewModel()) {
    val current = LocalContext.current

    val params =
        EntityExtractionParams.Builder("20、被外国学者称为“中国17世纪工艺百科全书”的是 （）\n" +
                "A、《九章算术》  B、《齐民要术》 \n" +
                "C、《资治通鉴》 D、《天工开物》")
            .build()
    val entityExtractor = EngineCollect.entityExtractor
    val addOnFailureListener = entityExtractor.annotate(params)
        .addOnSuccessListener {
            vm.decode(it)
        }
        .addOnFailureListener {
            it.printStackTrace()
            Toast.makeText(current,"fail",Toast.LENGTH_LONG).show()

        }
    Column(modifier) {
        Image(imageVector = Icons.Default.Favorite, contentDescription = "favorite", modifier = Modifier.size(100.dp,100.dp))
        Content(text = "20、被外国学者称为“中国17世纪工艺百科全书”的是 （）\n" +
                "A、《九章算术》  B、《齐民要术》 \n" +
                "C、《资治通鉴》 D、《天工开物》")

        AutoSplitPanel(vm.entityAnnotationList.value)
    }
}


@Composable
fun AutoSplitPanel(entityAnnotationList:List<EntityAnnotation>) {

//    LazyColumn{
//        items(items = entityAnnotationList, key = {it}) {
//            Text(text = it.annotatedText)
//        }
//    }
    for (entityAnnotation in entityAnnotationList) {
        Text(text = entityAnnotation.annotatedText)
    }



}


@Composable
private fun Content(modifier: Modifier = Modifier,text:String) {
    val current = LocalContext.current
    SelectionContainer() {
        Text(text = text, modifier = modifier.pointerInput(Unit) {
            detectTapGestures(
                onLongPress = {
                    show(current,"onLongPress")
                }
            )
        })
    }

}



