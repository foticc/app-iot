package com.example.navhostscreenapp.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.navhostscreenapp.ui.unprocessed.ColumnItem


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainPage() {
    Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)) {
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),value = "sdf", onValueChange = {

        })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = "sdf", onValueChange = {

        })
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .clip(CircleShape)
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .background(MaterialTheme.colorScheme.primary)
            .height(50.dp), onClick = { /*TODO*/ }) {
            Text(modifier = Modifier, text = "123", color = MaterialTheme.colorScheme.primaryContainer)
        }
    }
}