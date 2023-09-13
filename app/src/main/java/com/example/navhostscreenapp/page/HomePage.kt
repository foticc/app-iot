package com.example.navhostscreenapp.page

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.navhostscreenapp.ui.theme.NavhostScreenAppTheme

@Composable
fun Home(navController: NavController) {
    val list = List(50) {
        it + 1
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp).background(Color.LightGray)
    ) {
        items(list) {
            QuestionItem(item = it)
        }
    }
}


@Composable
fun QuestionItem(item: Int) {
    Row(
        modifier = Modifier
            .padding(3.dp)
            .border(width = 1.dp, color = Color.LightGray, shape = ShapeDefaults.Small)
            .background(Color.Blue)
    ) {
        Column(modifier = Modifier
            .weight(1f)
            .padding(1.dp)) {
            Text(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 2.dp), text = "$item")
            Text(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 2.dp), text = "more...........")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "open the item")
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NavhostScreenAppTheme {
        val navController = rememberNavController()
        Home(navController)
    }
}

