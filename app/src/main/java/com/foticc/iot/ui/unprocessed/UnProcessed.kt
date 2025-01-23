package com.foticc.iot.ui.unprocessed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.foticc.iot.database.entity.RecognitionResult

@Composable
fun UnProcessScreen(navController: NavController,vm: UnProcessViewModel = viewModel()) {
    val data by vm.recognitionResultDao.selectAll().collectAsState(initial = listOf());
    DataListColumn(list = data,navController)
}


@Composable
fun DataListVerticalGrid(list: List<RecognitionResult>) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp) ) {
        items(items = list, key = {it.id}) {
            VerticalGridItem(result = it)
        }
    }
}

@Composable
fun DataListColumn(list: List<RecognitionResult>, navController: NavController) {
    LazyColumn{
        items(items = list, key = {it.id}) {
            ColumnItem(it, click = {
                navController.navigate("unprocessed")
            })
        }
    }
}

@Composable
fun UnProcessItem(result: RecognitionResult) {
    val typography = MaterialTheme.typography
    Row(
        modifier = Modifier
            .clickable(onClick = { })
            .padding(16.dp)
    ) {
        ItemImage(
            result.uri,
            Modifier.padding(end = 16.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = result.content, style = typography.bodyMedium)
        }
        Icon(imageVector = Icons.Default.Favorite,contentDescription = "heart", tint = Color.Red)
    }
}


@Composable
fun ColumnItem(item: RecognitionResult, click:()->Unit, modifier: Modifier = Modifier) {
    val typography = MaterialTheme.typography
    Row(modifier = modifier
        .fillMaxWidth()
        .height(220.dp)
        .padding(16.dp)
        .clickable(onClick = click)


    ) {
        ColumnItemImage(item.uri)
        Text(
            text = item.content,
            style = typography.titleSmall
        )
        Text(
            text = item.content,
            style = typography.bodyMedium
        )
    }
}

@Composable
fun ColumnItemImage(uri: String = "http://img2015.zdface.com/20180907/cc7227eb99d19cb4c7d6bdf559d0389c.jpg") {
    val imageModifier = Modifier
        .height(150.dp)
        .clip(shape = androidx.compose.material.MaterialTheme.shapes.medium)

    val context = LocalContext.current
    AsyncImage(modifier = imageModifier,
        model = ImageRequest.Builder(context).data(uri).build(),
        contentDescription = "image")
}


@Composable
fun VerticalGridItem(result: RecognitionResult) {
    val typography = MaterialTheme.typography
    Card(
        shape = shapes.medium, modifier = Modifier
            .width(190.dp)
            .height(220.dp)
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            ItemImage(
                uri = result.uri, modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            )
        }
        Text(text = result.content, style = typography.bodyMedium)
    }
}


@Composable
fun ItemImage(uri:String ,modifier: Modifier = Modifier) {
    val context = LocalContext.current
    AsyncImage(modifier = modifier,
        model = ImageRequest.Builder(context).data(uri).build(),
        contentDescription = "image")
}


@Preview
@Composable
fun LazyGridList() {
    val listOf = arrayListOf(1..10)
    val mapIndexed = listOf.mapIndexed { index, intRange ->
        RecognitionResult(index, index.toString(), index.toString())
    }
    val rememberNavController = rememberNavController();
    DataListColumn(mapIndexed,rememberNavController)

}