package com.foticc.iot.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun Card() {
    Column {
        Icon(imageVector = Icons.Default.Notifications, contentDescription = "fsf")
        Text(text = "Title", style = MaterialTheme.typography.titleMedium)
        Text(text = "Title", style = MaterialTheme.typography.bodyMedium)
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Notifications, contentDescription = "fsf")
        }
    }
}