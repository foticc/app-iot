package com.example.navhostscreenapp.page

import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun Home(navController: NavController) {
    Text(text = "Home")
    Button(onClick = { navController.navigate("info")}) {
        Text(text = "go to")
    }
}

